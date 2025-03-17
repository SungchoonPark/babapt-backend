package com.babpat.server.domain.settlement.service;

import com.babpat.server.domain.settlement.dto.response.SettlementInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SettlementQueryService {
    Page<SettlementInfo> getSettlementStates(String authUsername, Pageable pageable);

}
