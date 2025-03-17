package com.babpat.server.domain.settlement.dto.response;

import com.babpat.server.domain.settlement.entity.enums.PayerStatus;

import java.time.LocalDateTime;

public record AlarmResponse(
        Long settlementId,
        Integer totalPrice,
        Integer perPrice,
        String accountNumber,
        String bankName,
        Integer totalPeopleCount,
        String accountHolder,
        String restaurantName,
        String leaderNickname,
        LocalDateTime babpatAt,
        PayerStatus payStatus
) {}