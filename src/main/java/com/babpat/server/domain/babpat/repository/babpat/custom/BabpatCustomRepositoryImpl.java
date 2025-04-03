package com.babpat.server.domain.babpat.repository.babpat.custom;

import static com.babpat.server.domain.babpat.entity.QBabpat.babpat;
import static com.babpat.server.domain.babpat.entity.QParticipation.participation;
import static com.babpat.server.domain.member.entity.QMember.member;
import static com.babpat.server.domain.restaurant.entity.QRestaurant.restaurant;

import com.babpat.server.domain.babpat.dto.request.SearchCond;
import com.babpat.server.domain.babpat.dto.response.BabpatInfoRespDto;
import com.babpat.server.domain.babpat.dto.response.PartBabpatId;
import com.babpat.server.domain.babpat.entity.enums.BabpatStatus;
import com.babpat.server.domain.babpat.entity.enums.PatType;
import com.babpat.server.domain.restaurant.dto.response.RestaurantInfo;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
@RequiredArgsConstructor
public class BabpatCustomRepositoryImpl implements BabpatCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<BabpatInfoRespDto> getBabpats(SearchCond searchCond, Pageable pageable) {
    List<Tuple> results = jpaQueryFactory
        .select(
            babpat.id, babpat.comment, babpat.headCount, babpat.patDate, babpat.patTime,
            babpat.mealSpeed,
            member.id, member.name, member.nickname, member.track,
            restaurant.name, restaurant.menus, restaurant.category1, restaurant.category2,
            restaurant.thumbnail
        )
        .from(babpat)
        .join(babpat.member, member)
        .join(babpat.restaurant, restaurant)
        .where(
            foodEq(searchCond.foodCond()),
            peopleEq(searchCond.peopleCond()),
            keywordEq(searchCond.keywordCond()),
            patEq(searchCond.patCond()),
            babpat.babpatStatus.in(BabpatStatus.ONGOING, BabpatStatus.FULL)
        )
        .orderBy(babpat.createdAt.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Map<Long, List<BabpatInfoRespDto.JoinMemberProfile>> maps = new HashMap<>();
    for (Tuple result : results) {
      List<BabpatInfoRespDto.JoinMemberProfile> babpatJoiners = jpaQueryFactory.select(
              Projections.constructor(
                  BabpatInfoRespDto.JoinMemberProfile.class,
                  member.id,
                  member.name,
                  member.nickname,
                  member.track
              )
          )
          .from(participation)
          .join(participation.member, member)
          .where(participation.babpat.id.eq(result.get(babpat.id)))
          .fetch();

      maps.put(result.get(babpat.id), babpatJoiners);
    }

    List<BabpatInfoRespDto> babpatDataList = results.stream()
        .map(tuple -> new BabpatInfoRespDto(
            new RestaurantInfo(
                tuple.get(restaurant.name),
                String.valueOf(tuple.get(restaurant.menus)),
                parsingCategories(tuple.get(restaurant.category1), tuple.get(restaurant.category2)),
                tuple.get(restaurant.thumbnail)
            ),
            new BabpatInfoRespDto.BabpatInfo(
                tuple.get(babpat.id),
                tuple.get(babpat.comment),
                new BabpatInfoRespDto.Capacity(
                    Optional.ofNullable(tuple.get(babpat.headCount)).orElse(0),
                    countParticipation(tuple.get(babpat.id))
                ),
                tuple.get(babpat.mealSpeed),
                tuple.get(babpat.patDate),
                tuple.get(babpat.patTime),
                new BabpatInfoRespDto.LeaderProfile(
                    tuple.get(member.id),
                    tuple.get(member.name),
                    tuple.get(member.nickname),
                    tuple.get(member.track)
                ),
                maps.get(tuple.get(babpat.id))
            )
        ))
        .toList();

    JPAQuery<Long> countQuery = jpaQueryFactory
        .select(babpat.count())
        .from(babpat)
        .where(
            foodEq(searchCond.foodCond()),
            peopleEq(searchCond.peopleCond()),
            keywordEq(searchCond.keywordCond()),
            patEq(searchCond.patCond()),
            babpat.babpatStatus.in(BabpatStatus.ONGOING, BabpatStatus.FULL)
        )
        .leftJoin(babpat.restaurant, restaurant);

    // BabpatData 리스트를 직접 페이지네이션
    return PageableExecutionUtils.getPage(babpatDataList, pageable, countQuery::fetchOne);
  }

  @Override
  public PartBabpatId getParticipatingBabpat(String authUsername) {
    List<Long> result = jpaQueryFactory
        .select(participation.babpat.id)
        .from(participation)
        .where(participation.member.username.eq(authUsername))
        .fetch();

    return new PartBabpatId(Optional.ofNullable(result).orElseGet(Collections::emptyList));
  }

  private BooleanExpression keywordEq(String keywordCond) {
    if (StringUtils.isBlank(keywordCond)) {
      return null;
    }
    return babpat.comment.containsIgnoreCase(keywordCond);
  }

  private BooleanExpression foodEq(List<String> foodCond) {
    if (CollectionUtils.isEmpty(foodCond)) {
      return null;
    }
    return restaurant.category1.in(foodCond);
  }

  private BooleanExpression peopleEq(List<Integer> peopleCond) {
    if (CollectionUtils.isEmpty(peopleCond)) {
      return null;
    }
    return babpat.headCount.in(peopleCond);
  }

  private BooleanExpression patEq(String patCond) {
    if (patCond.isBlank()) {
      return null;
    }

    return babpat.patType.eq(PatType.fromString(patCond));
  }

  private Integer countParticipation(Long babpatId) {
    return Optional.ofNullable(jpaQueryFactory
            .select(participation.id.count())
            .from(participation)
            .where(participation.babpat.id.eq(babpatId))
            .fetchOne())
        .map(Math::toIntExact)
        .orElse(0);
  }

  private List<String> parsingCategories(String category1, String category2) {
    return Arrays.stream((category1 + "," + category2).split(","))
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .toList();
  }
}
