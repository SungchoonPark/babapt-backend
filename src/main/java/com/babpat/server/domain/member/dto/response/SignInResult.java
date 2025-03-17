package com.babpat.server.domain.member.dto.response;

import com.babpat.server.config.jwt.dto.AuthTokens;
import com.babpat.server.domain.member.entity.enums.Track;

public record SignInResult(
        Long id,
        String name,
        String nickname,
        Track track,
        AuthTokens authTokens
) {}
