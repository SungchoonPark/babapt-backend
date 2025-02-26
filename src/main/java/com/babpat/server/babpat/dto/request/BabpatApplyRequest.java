package com.babpat.server.babpat.dto.request;

import com.babpat.server.babpat.entity.Participation;

public record BabpatApplyRequest(
        Long userId,
        Long babpatId
) {

    public Participation toEntity() {
        return Participation.builder()
                .babpatId(babpatId)
                .memberId(userId)
                .build();
    }
}
