package com.babpat.server.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record IdCheckRequestDto(
        @NotBlank String id
) {
}
