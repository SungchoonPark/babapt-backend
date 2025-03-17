package com.babpat.server.domain.settlement.repository.custom;

import com.babpat.server.domain.member.entity.QMember;
import com.babpat.server.domain.settlement.dto.response.SettlementInfo;
import com.babpat.server.domain.settlement.entity.enums.SettlementStatus;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.babpat.server.domain.babpat.entity.QBabpat.babpat;
import static com.babpat.server.domain.babpat.entity.QParticipation.participation;
import static com.babpat.server.domain.member.entity.QMember.member;
import static com.babpat.server.domain.restaurant.entity.QRestaurant.restaurant;
import static com.babpat.server.domain.settlement.dto.response.SettlementInfo.*;
import static com.babpat.server.domain.settlement.entity.QPayer.payer;
import static com.babpat.server.domain.settlement.entity.QSettlement.settlement;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SettlementCustomRepositoryImpl implements SettlementCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<SettlementInfo> getBabpatSettlementStates(Long memberId, Pageable pageable) {
        // 1단계: 멤버가 참여한 고유한 밥팟 ID 리스트 가져오기 (중복 제거)
        List<Tuple> babpatBasicInfos = jpaQueryFactory
                .select(
                        babpat.id,
                        babpat.createdAt
                )
                .distinct()
                .from(participation)
                .join(participation.babpat, babpat)
                .where(participation.member.id.eq(memberId))
                .orderBy(babpat.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 결과 리스트 초기화
        List<SettlementInfo> results = new ArrayList<>();

        // 각 밥팟에 대해 상세 정보 조회
        for (Tuple babpatBasicInfo : babpatBasicInfos) {
            Long babpatId = babpatBasicInfo.get(babpat.id);

            // 2단계: 밥팟 상세 정보 (레스토랑, 정산 상태)
            Tuple babpatDetailInfo = jpaQueryFactory
                    .select(
                            restaurant.name,
                            settlement.settlementStatus.coalesce(SettlementStatus.BEFORE)
                    )
                    .from(babpat)
                    .leftJoin(babpat.restaurant, restaurant)
                    .leftJoin(settlement)
                    .on(settlement.babpat.eq(babpat))
                    .where(babpat.id.eq(babpatId))
                    .fetchOne();

            // 3단계: 밥팟 참여자 정보
            List<ParticipationInfo> participants = jpaQueryFactory
                    .select(Projections.constructor(
                            ParticipationInfo.class,
                            member.nickname,
                            member.name,
                            member.track
                    ))
                    .from(participation)
                    .join(participation.member, member)
                    .where(participation.babpat.id.eq(babpatId))
                    .fetch();

            // 4단계: SettlementInfo 객체 생성 및 결과 추가
            results.add(new SettlementInfo(
                    babpatId,
                    babpatDetailInfo.get(restaurant.name),
                    babpatBasicInfo.get(babpat.createdAt),
                    babpatDetailInfo.get(settlement.settlementStatus.coalesce(SettlementStatus.BEFORE)),
                    participants
            ));
        }

        // 총 개수 쿼리 (distinct 사용)
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(babpat.countDistinct())
                .from(participation)
                .join(participation.babpat, babpat)
                .where(participation.member.id.eq(memberId));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }
}
