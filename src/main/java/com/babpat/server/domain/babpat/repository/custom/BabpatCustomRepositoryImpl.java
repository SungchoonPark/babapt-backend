package com.babpat.server.domain.babpat.repository.custom;

import com.babpat.server.domain.babpat.dto.request.SearchCond;
import com.babpat.server.domain.babpat.dto.response.BabpatInfoRespDto;
import com.babpat.server.domain.babpat.entity.enums.BabpatStatus;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.babpat.server.domain.babpat.entity.QBabpat.babpat;
import static com.babpat.server.domain.babpat.entity.QParticipation.participation;
import static com.babpat.server.domain.member.entity.QMember.member;
import static com.babpat.server.domain.restaurant.entity.QRestaurant.restaurant;

@Repository
@RequiredArgsConstructor
public class BabpatCustomRepositoryImpl implements BabpatCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<BabpatInfoRespDto> getBabpats(SearchCond searchCond, Pageable pageable) {
        List<Tuple> results = jpaQueryFactory
                .select(
                        babpat.id, babpat.comment, babpat.headCount, babpat.patDate, babpat.patTime, babpat.mealSpeed,
                        member.name, member.nickname, member.track,
                        restaurant.name, restaurant.menus, restaurant.category1, restaurant.category2, restaurant.thumbnail
                )
                .from(babpat)
                .join(babpat.member, member)
                .join(babpat.restaurant, restaurant)
                .where(
                        foodEq(searchCond.foodCond()),
                        peopleEq(searchCond.peopleCond()),
                        keywordEq(searchCond.keywordCond()),
                        babpat.babpatStatus.in(BabpatStatus.ONGOING, BabpatStatus.FULL)
                )
                .orderBy(babpat.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<BabpatInfoRespDto.BabpatData> babpatDataList = results.stream()
                .map(tuple -> new BabpatInfoRespDto.BabpatData(
                        new BabpatInfoRespDto.RestaurantInfo(
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
                                        tuple.get(member.name),
                                        tuple.get(member.nickname),
                                        tuple.get(member.track)
                                )
                        )
                ))
                .toList();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(babpat.count())
                .from(babpat)
                .where(
                        foodEq(searchCond.foodCond()),
                        peopleEq(searchCond.peopleCond()),
                        keywordEq(searchCond.keywordCond())
                )
                .leftJoin(babpat.restaurant, restaurant);

        List<BabpatInfoRespDto> responseDtoList = List.of(new BabpatInfoRespDto(babpatDataList));
        return PageableExecutionUtils.getPage(responseDtoList, pageable, countQuery::fetchOne);
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
