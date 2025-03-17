package com.babpat.server.domain.settlement.repository.custom;

import com.babpat.server.domain.settlement.entity.Payer;
import com.babpat.server.domain.settlement.entity.enums.PayerStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.babpat.server.domain.settlement.entity.QPayer.payer;

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
}
