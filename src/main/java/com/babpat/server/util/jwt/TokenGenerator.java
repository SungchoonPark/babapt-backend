package com.babpat.server.util.jwt;

import com.babpat.server.config.jwt.dto.AuthTokens;
import com.babpat.server.config.jwt.enums.TokenType;
import com.babpat.server.domain.member.entity.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenGenerator {
    private final JwtUtil jwtUtil;

    public AuthTokens generateTokenWithoutRFToken(String id, RoleType roleType) {
        String accessToken = jwtUtil.createToken(id, TokenType.ACCESS_TOKEN, roleType);
        String refreshToken = jwtUtil.createToken(id, TokenType.REFRESH_TOKEN, roleType);

        return AuthTokens.of(accessToken, refreshToken);
    }

    public AuthTokens generateTokenWithRFToken(String id, String refreshToken, RoleType roleType) {

        String accessToken = jwtUtil.createToken(id, TokenType.ACCESS_TOKEN, roleType);
        return AuthTokens.of(accessToken, refreshToken);
    }
}
