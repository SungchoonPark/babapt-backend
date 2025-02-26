package com.babpat.server.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequestDto(
        @NotBlank String id,
        @NotBlank String password
) {
}
