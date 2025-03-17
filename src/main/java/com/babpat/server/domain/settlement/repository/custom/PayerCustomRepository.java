package com.babpat.server.domain.settlement.repository.custom;

import com.babpat.server.domain.settlement.entity.Payer;

import java.util.Optional;

public interface PayerCustomRepository {
    Optional<Payer> findBySettlementIdAndUsername(Long settlementId, String payerUsername);
    boolean isSettlementComplete(Long settlementId);

}
