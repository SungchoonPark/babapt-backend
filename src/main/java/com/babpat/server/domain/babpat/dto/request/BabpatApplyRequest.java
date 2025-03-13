package com.babpat.server.domain.babpat.dto.request;

import com.babpat.server.domain.babpat.entity.Babpat;
import com.babpat.server.domain.babpat.entity.Participation;
import com.babpat.server.domain.babpat.entity.enums.ParticipationStatus;
import com.babpat.server.domain.member.entity.Member;
import jakarta.validation.constraints.NotNull;

public record BabpatApplyRequest(
        @NotNull Long babpatId
) {

    public Participation toEntity(Babpat babpat, Member member) {
        return Participation.builder()
                .babpat(babpat)
                .member(member)
                .participationStatus(ParticipationStatus.JOINED)
                .build();
    }
}
