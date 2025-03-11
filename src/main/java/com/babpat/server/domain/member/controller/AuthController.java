package com.babpat.server.domain.member.controller;

import com.babpat.server.common.dto.ApiResponse;
import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.domain.member.dto.request.SignInRequestDto;
import com.babpat.server.domain.member.dto.request.SignupRequestDto;
import com.babpat.server.domain.member.dto.response.SignInResponseDto;
import com.babpat.server.domain.member.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody @Valid SignupRequestDto requestDto) {
        authService.register(requestDto);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<SignInResponseDto>> login(@RequestBody @Valid SignInRequestDto signInRequestDto) {
        SignInResponseDto response = authService.login(signInRequestDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(
                response,
                CustomResponseStatus.SUCCESS.withMessage("로그인 성공"))
        );
    }
}
