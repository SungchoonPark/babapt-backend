package com.babpat.server.domain.settlement.service.impl;

import com.babpat.server.domain.settlement.repository.SettlementRepository;
import com.babpat.server.domain.settlement.service.SettlementCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SettlementCommandServiceImpl implements SettlementCommandService {
    private final SettlementRepository settlementRepository;

}
