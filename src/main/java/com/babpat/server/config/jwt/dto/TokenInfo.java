package com.babpat.server.config.jwt.dto;

import lombok.Builder;

@Builder
public record TokenInfo(
        Long id,
        String role
) {
}
