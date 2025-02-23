package com.babpat.server.member.dto.request;

public record SignInRequestDto(
        String id,
        String password
) {
}
