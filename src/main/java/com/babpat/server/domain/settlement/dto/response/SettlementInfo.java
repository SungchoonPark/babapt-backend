package com.babpat.server.domain.settlement.dto.response;

import com.babpat.server.domain.member.entity.enums.Track;
import com.babpat.server.domain.settlement.entity.enums.SettlementStatus;

import java.time.LocalDateTime;
import java.util.List;

public record SettlementInfo(
        Long babpatId,
        String restaurantName,
        LocalDateTime settlementAt,
        SettlementStatus settlementStatus,
        List<PayerInfo> payers
) {
    public record PayerInfo(
            String nickname,
            String name,
            Track track
    ) {}
}