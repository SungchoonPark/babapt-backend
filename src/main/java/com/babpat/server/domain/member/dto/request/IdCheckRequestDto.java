package com.babpat.server.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record IdCheckRequestDto(
        @NotBlank String id
) {
}
