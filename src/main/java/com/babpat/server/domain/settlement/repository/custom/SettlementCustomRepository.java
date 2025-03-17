package com.babpat.server.domain.settlement.repository.custom;

import com.babpat.server.domain.settlement.dto.response.SettlementInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SettlementCustomRepository {
    Page<SettlementInfo> getBabpatSettlementStates(Long memberId, Pageable pageable);
}
