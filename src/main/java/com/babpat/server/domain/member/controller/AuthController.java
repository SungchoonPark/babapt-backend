package com.babpat.server.domain.member.controller;

import com.babpat.server.common.dto.ApiResponse;
import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.config.jwt.dto.AuthTokens;
import com.babpat.server.domain.member.dto.request.SignInRequestDto;
import com.babpat.server.domain.member.dto.request.SignupRequestDto;
import com.babpat.server.domain.member.dto.response.SignInResponseDto;
import com.babpat.server.domain.member.service.auth.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody @Valid SignupRequestDto requestDto) {
        authService.register(requestDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(
                CustomResponseStatus.SUCCESS_WITH_NO_CONTENT.withMessage("로그인 성공"))
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<SignInResponseDto>> login(
            @RequestBody @Valid SignInRequestDto signInRequestDto,
            HttpServletResponse response
    ) {
        SignInResponseDto responseDto = authService.login(signInRequestDto);

        Cookie refreshTokenCookie = new Cookie("refreshToken", responseDto.authTokens().refreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setSecure(true);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(
                responseDto,
                CustomResponseStatus.SUCCESS.withMessage("로그인 성공"))
        );
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<AuthTokens>> reissue(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) {
        AuthTokens authTokens = authService.reissue(refreshToken);

        Cookie refreshTokenCookie = new Cookie("refreshToken", authTokens.refreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setSecure(true);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(
                authTokens,
                CustomResponseStatus.SUCCESS.withMessage("토큰 재발급 성공"))
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(
            @RequestHeader("Authorization") String accessToken
    ) {
        authService.logout(accessToken);

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(
                CustomResponseStatus.SUCCESS_WITH_NO_CONTENT.withMessage("로그아웃이 완료되었습니다."))
        );
    }
}
