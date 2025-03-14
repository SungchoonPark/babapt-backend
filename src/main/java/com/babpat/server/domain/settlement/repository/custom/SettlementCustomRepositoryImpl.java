package com.babpat.server.domain.settlement.repository.custom;

import com.babpat.server.domain.member.entity.QMember;
import com.babpat.server.domain.settlement.dto.response.SettlementInfo;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.babpat.server.domain.babpat.entity.QBabpat.babpat;
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
    public Page<SettlementInfo> getSettlementStates(Long memberId, Pageable pageable) {
        // 1. Settlement 데이터 조회
        List<Tuple> settlementTuples = jpaQueryFactory
                .select(
                        settlement.id,
                        babpat.id,
                        restaurant.name,
                        settlement.createdAt,
                        settlement.settlementStatus
                )
                .from(settlement)
                .join(settlement.babpat, babpat)
                .join(babpat.restaurant, restaurant)
                .join(settlement.member, member) // 정산 요청자
                .where(member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        log.info("Settlements found: {}", settlementTuples.size());

        // Settlement ID 목록 추출
        List<Long> settlementIds = settlementTuples.stream()
                .map(tuple -> tuple.get(settlement.id))
                .toList();

        // Settlement가 없으면 빈 페이지 반환
        if (settlementIds.isEmpty()) {
            return Page.empty(pageable);
        }

        // 2. Payer 정보 조회
        QMember payerMember = new QMember("payerMember");

        List<Tuple> payerTuples = jpaQueryFactory
                .select(
                        settlement.id,
                        payerMember.nickname,
                        payerMember.name,
                        payerMember.track
                )
                .from(payer)
                .join(payer.member, payerMember) // 별칭 적용
                .join(payer.settlement, settlement)
                .where(settlement.id.in(settlementIds))
                .fetch();

        // 3. Payer 정보를 Settlement ID 기준으로 그룹핑
        Map<Long, List<PayerInfo>> payerMap = payerTuples.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(settlement.id),
                        Collectors.mapping(tuple -> new PayerInfo(
                                tuple.get(payerMember.nickname),
                                tuple.get(payerMember.name),
                                tuple.get(payerMember.track)
                        ), Collectors.toList())
                ));

        // 4. SettlementInfo 최종적으로 생성
        List<SettlementInfo> settlements = settlementTuples.stream()
                .map(tuple -> new SettlementInfo(
                        tuple.get(babpat.id),
                        tuple.get(restaurant.name),
                        tuple.get(settlement.createdAt),
                        tuple.get(settlement.settlementStatus),
                        payerMap.getOrDefault(tuple.get(settlement.id), List.of()) // Payer 정보 추가
                ))
                .toList();

        // 5. 총 개수 조회 쿼리 (페이징 최적화)
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(settlement.count())
                .from(settlement)
                .join(settlement.member, member)
                .where(member.id.eq(memberId));

        return PageableExecutionUtils.getPage(
                settlements,  // SettlementInfo 리스트를 직접 반환
                pageable,
                countQuery::fetchOne
        );
    }
}
