package com.babpat.server.domain.settlement.repository.custom;

import static com.babpat.server.domain.babpat.entity.QBabpat.babpat;
import static com.babpat.server.domain.babpat.entity.QParticipation.participation;
import static com.babpat.server.domain.member.entity.QMember.member;
import static com.babpat.server.domain.restaurant.entity.QRestaurant.restaurant;
import static com.babpat.server.domain.settlement.dto.response.SettlementInfo.ParticipationInfo;
import static com.babpat.server.domain.settlement.entity.QSettlement.settlement;

import com.babpat.server.domain.babpat.entity.Babpat;
import com.babpat.server.domain.babpat.entity.Participation;
import com.babpat.server.domain.settlement.dto.response.SettlementInfo;
import com.babpat.server.domain.settlement.entity.enums.SettlementStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SettlementCustomRepositoryImpl implements SettlementCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    //    @Override
//    public Page<SettlementInfo> getBabpatSettlementStates(Long memberId, Pageable pageable) {
//        // 1단계: 멤버가 참여한 고유한 밥팟 ID 리스트 가져오기 (중복 제거)
//        List<Tuple> babpatBasicInfos = jpaQueryFactory
//            .select(
//                babpat.id,
//                babpat.createdAt
//            )
//            .distinct()
//            .from(participation)
//            .join(participation.babpat, babpat)
//            .where(participation.member.id.eq(memberId))
//            .orderBy(babpat.createdAt.desc())
//            .offset(pageable.getOffset())
//            .limit(pageable.getPageSize())
//            .fetch();
//
//        // 결과 리스트 초기화
//        List<SettlementInfo> results = new ArrayList<>();
//
//        // 각 밥팟에 대해 상세 정보 조회
//        for (Tuple babpatBasicInfo : babpatBasicInfos) {
//            Long babpatId = babpatBasicInfo.get(babpat.id);
//
//            // 2단계: 밥팟 상세 정보 (레스토랑, 정산 상태)
//            Tuple babpatDetailInfo = jpaQueryFactory
//                .select(
//                    restaurant.name,
//                    settlement.settlementStatus.coalesce(SettlementStatus.BEFORE)
//                )
//                .from(babpat)
//                .leftJoin(babpat.restaurant, restaurant)
//                .leftJoin(settlement)
//                .on(settlement.babpat.eq(babpat))
//                .where(babpat.id.eq(babpatId))
//                .fetchOne();
//
//            // 3단계: 밥팟 참여자 정보
//            List<ParticipationInfo> participants = jpaQueryFactory
//                .select(Projections.constructor(
//                    ParticipationInfo.class,
//                    member.nickname,
//                    member.name,
//                    member.track
//                ))
//                .from(participation)
//                .join(participation.member, member)
//                .where(participation.babpat.id.eq(babpatId))
//                .fetch();
//
//            // 4단계: SettlementInfo 객체 생성 및 결과 추가
//            results.add(new SettlementInfo(
//                babpatId,
//                babpatDetailInfo.get(restaurant.name),
//                babpatBasicInfo.get(babpat.createdAt),
//                babpatDetailInfo.get(settlement.settlementStatus.coalesce(SettlementStatus.BEFORE)),
//                participants
//            ));
//        }
//
//        // 총 개수 쿼리 (distinct 사용)
//        JPAQuery<Long> countQuery = jpaQueryFactory
//            .select(babpat.countDistinct())
//            .from(participation)
//            .join(participation.babpat, babpat)
//            .where(participation.member.id.eq(memberId));
//
//        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
//    }
    @Override
    public Page<SettlementInfo> getBabpatSettlementStates(Long memberId, Pageable pageable) {
        // 1. Participation 페이징 + Babpat + Restaurant 엔티티 fetchJoin
        List<Participation> participations = jpaQueryFactory
            .selectFrom(participation)
            .join(participation.babpat, babpat).fetchJoin()
            .join(babpat.restaurant, restaurant).fetchJoin()
            .where(participation.member.id.eq(memberId))
            .orderBy(babpat.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // 2. 참여한 Babpat ID 수집
        List<Long> babpatIds = participations.stream()
            .map(p -> p.getBabpat().getId())
            .distinct()
            .toList();

        // 3. Settlement 상태 일괄 조회 → Map<babpatId, SettlementStatus>
        Map<Long, SettlementStatus> settlementMap = jpaQueryFactory
            .select(settlement.babpat.id, settlement.settlementStatus)
            .from(settlement)
            .where(settlement.babpat.id.in(babpatIds))
            .fetch()
            .stream()
            .collect(Collectors.toMap(
                t -> t.get(settlement.babpat.id),
                t -> t.get(settlement.settlementStatus)
            ));

        // 4. 참여자 정보 일괄 조회 → Map<babpatId, List<ParticipationInfo>>
        Map<Long, List<ParticipationInfo>> participantsMap = jpaQueryFactory
            .select(Projections.constructor(
                ParticipationInfo.class,
                member.nickname,
                member.name,
                member.track,
                participation.babpat.id
            ))
            .from(participation)
            .join(participation.member, member)
            .where(participation.babpat.id.in(babpatIds))
            .fetch()
            .stream()
            .collect(Collectors.groupingBy(ParticipationInfo::babpatId));

        // 5. SettlementInfo 조립
        List<SettlementInfo> results = participations.stream()
            .map(p -> {
                Babpat b = p.getBabpat();
                Long id = b.getId();

                return new SettlementInfo(
                    id,
                    b.getRestaurant().getName(),
                    b.getCreatedAt(),
                    settlementMap.getOrDefault(id, SettlementStatus.BEFORE),
                    participantsMap.getOrDefault(id, List.of())
                );
            })
            .distinct()
            .toList();

        // 6. 전체 개수 쿼리
        JPAQuery<Long> countQuery = jpaQueryFactory
            .select(babpat.countDistinct())
            .from(participation)
            .join(participation.babpat, babpat)
            .where(participation.member.id.eq(memberId));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }
}
