package com.babpat.server.domain.member.service.auth;

import com.babpat.server.domain.member.dto.request.SignInRequestDto;
import com.babpat.server.domain.member.dto.request.SignupRequestDto;
import com.babpat.server.domain.member.dto.response.SignInResponseDto;

public interface AuthService {
    void register(SignupRequestDto requestDto);

    SignInResponseDto login(SignInRequestDto signInRequestDto);
}
