package com.babpat.server.domain.settlement.dto.response;

import com.babpat.server.domain.member.entity.enums.Track;
import com.babpat.server.domain.settlement.entity.enums.SettlementStatus;
import java.time.LocalDateTime;
import java.util.List;

public record SettlementInfo(
    Long babpatId,
    String restaurantName,
    LocalDateTime babpatAt,
    SettlementStatus settlementStatus,
    List<ParticipationInfo> payers // participations 로 변수명 변경
) {

    public record ParticipationInfo(
        String nickname,
        String name,
        Track track,
        Long babpatId
    ) {

    }
}