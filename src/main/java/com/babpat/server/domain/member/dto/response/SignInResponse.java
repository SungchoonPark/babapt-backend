package com.babpat.server.domain.member.dto.response;

import com.babpat.server.domain.member.entity.enums.Track;

public record SignInResponse(
        Long id,
        String name,
        String nickname,
        Track track,
        AuthToken authToken
) {
    public record AuthToken(String accessToken) {}

    public static SignInResponse of(SignInResult signInResult) {
        return new SignInResponse(
                signInResult.id(),
                signInResult.name(),
                signInResult.nickname(),
                signInResult.track(),
                new AuthToken(signInResult.authTokens().accessToken())
        );
    }
}
