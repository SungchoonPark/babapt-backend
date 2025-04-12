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
    List<ParticipationInfo> payers
) {

  public record ParticipationInfo(
      Long id,
      String nickname,
      String name,
      Track track
  ) {

  }
}