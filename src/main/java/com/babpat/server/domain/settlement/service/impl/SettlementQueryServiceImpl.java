package com.babpat.server.domain.settlement.service.impl;

import com.babpat.server.domain.settlement.repository.SettlementRepository;
import com.babpat.server.domain.settlement.service.SettlementQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SettlementQueryServiceImpl implements SettlementQueryService {
    private final SettlementRepository settlementRepository;

}
