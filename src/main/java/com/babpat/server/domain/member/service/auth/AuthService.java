package com.babpat.server.domain.member.service.auth;

import com.babpat.server.config.jwt.dto.AuthTokens;
import com.babpat.server.domain.member.dto.request.SignInRequestDto;
import com.babpat.server.domain.member.dto.request.SignupRequestDto;
import com.babpat.server.domain.member.dto.response.SignInResult;

public interface AuthService {
    void register(SignupRequestDto requestDto);

    SignInResult login(SignInRequestDto signInRequestDto);

    AuthTokens reissue(String refreshToken);

    void logout(String accessToken);
}
