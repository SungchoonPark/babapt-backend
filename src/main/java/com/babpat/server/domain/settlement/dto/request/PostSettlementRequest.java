package com.babpat.server.domain.settlement.dto.request;

import com.babpat.server.domain.babpat.entity.Babpat;
import com.babpat.server.domain.member.entity.Member;
import com.babpat.server.domain.settlement.entity.Settlement;
import com.babpat.server.domain.settlement.entity.enums.SettlementStatus;

public record PostSettlementRequest(
    Long babpatId,
    Integer totalPrice,
    Integer perPrice,
    Integer memberCount,
    String accountNumber,
    String bankName,
    String accountHolder
) {

    public Settlement toEntity(Babpat babpat, Member member) {
        return Settlement.builder()
                .babpat(babpat)
                .member(member)
                .totalPrice(totalPrice)
                .perPrice(perPrice)
                .memberCount(memberCount)
                .accountNumber(accountNumber)
                .bankName(bankName)
                .accountHolder(accountHolder)
                .settlementStatus(SettlementStatus.PENDING)
                .build();
    }
}