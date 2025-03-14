package com.babpat.server.domain.settlement.service;

import com.babpat.server.domain.settlement.dto.request.PostSettlementRequest;

public interface SettlementCommandService {
    void postSettlement(PostSettlementRequest postSettlementRequest, String authUsername);
}
