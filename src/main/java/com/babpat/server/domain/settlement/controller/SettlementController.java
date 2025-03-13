package com.babpat.server.domain.settlement.controller;

import com.babpat.server.domain.settlement.service.SettlementCommandService;
import com.babpat.server.domain.settlement.service.SettlementQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SettlementController {
    private final SettlementCommandService commandService;
    private final SettlementQueryService queryService;

}
