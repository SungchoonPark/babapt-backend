package com.babpat.server.domain.settlement.repository.custom;

import com.babpat.server.domain.settlement.dto.response.AlarmResponse;
import com.babpat.server.domain.settlement.entity.Payer;
import com.babpat.server.domain.settlement.entity.enums.PayerStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.babpat.server.domain.babpat.entity.QBabpat.babpat;
import static com.babpat.server.domain.member.entity.QMember.member;
import static com.babpat.server.domain.restaurant.entity.QRestaurant.restaurant;
import static com.babpat.server.domain.settlement.entity.QPayer.payer;
import static com.babpat.server.domain.settlement.entity.QSettlement.settlement;

@Repository
@RequiredArgsConstructor
public class PayerCustomRepositoryImpl implements PayerCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Optional<Payer> findBySettlementIdAndUsername(Long settlementId, String payerUsername) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .select(payer)
                        .from(payer)
                        .where(payer.settlement.id.eq(settlementId), payer.member.username.eq(payerUsername))
                        .fetchOne()
        );
    }

    @Override
    public boolean isSettlementComplete(Long settlementId) {
        boolean exists = jpaQueryFactory
                .selectOne()
                .from(payer)
                .where(
                        payer.settlement.id.eq(settlementId),
                        payer.payerStatus.eq(PayerStatus.UNPAID)
                )
                .fetchFirst() != null;

        return !exists;
    }

    @Override
    public List<AlarmResponse> getRecentAlarms(String username) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                AlarmResponse.class,
                                settlement.id,
                                settlement.totalPrice,
                                settlement.perPrice,
                                settlement.accountNumber,
                                settlement.bankName,
                                settlement.memberCount,
                                settlement.accountHolder,
                                restaurant.name,
                                member.nickname,
                                babpat.createdAt,
                                payer.payerStatus
                        )
                )
                .from(payer)
                .leftJoin(payer.settlement, settlement)
                .leftJoin(payer.member, member)
                .leftJoin(settlement.babpat, babpat)
                .leftJoin(babpat.restaurant, restaurant)
                .where(member.username.eq(username))
                .orderBy(payer.createdAt.desc())
                .limit(5)
                .fetch();
    }
}
