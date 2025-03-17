package com.babpat.server.domain.settlement.service;

import com.babpat.server.domain.settlement.dto.response.AlarmResponse;
import com.babpat.server.domain.settlement.dto.response.SettlementInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SettlementQueryService {
    Page<SettlementInfo> getBabpatSettlementStates(String authUsername, Pageable pageable);
    List<AlarmResponse> getSettlementAlarms(String authUsername);
}
