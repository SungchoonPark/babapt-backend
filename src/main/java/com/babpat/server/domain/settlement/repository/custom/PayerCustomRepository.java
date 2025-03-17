package com.babpat.server.domain.settlement.repository.custom;

import com.babpat.server.domain.settlement.dto.response.AlarmResponse;
import com.babpat.server.domain.settlement.entity.Payer;

import java.util.List;
import java.util.Optional;

public interface PayerCustomRepository {
    Optional<Payer> findBySettlementIdAndUsername(Long settlementId, String payerUsername);
    boolean isSettlementComplete(Long settlementId);
    List<AlarmResponse> getRecentAlarms(String username);
}
