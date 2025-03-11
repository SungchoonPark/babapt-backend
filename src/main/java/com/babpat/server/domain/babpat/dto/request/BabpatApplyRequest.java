package com.babpat.server.domain.babpat.dto.request;

import com.babpat.server.domain.babpat.entity.Participation;
import jakarta.validation.constraints.NotNull;

public record BabpatApplyRequest(
        @NotNull Long userId,
        @NotNull Long babpatId
) {

    public Participation toEntity() {
        return Participation.builder()
                .babpatId(babpatId)
                .memberId(userId)
                .build();
    }
}
