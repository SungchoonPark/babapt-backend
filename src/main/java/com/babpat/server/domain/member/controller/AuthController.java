package com.babpat.server.domain.member.controller;

import com.babpat.server.common.dto.ApiResponse;
import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.config.jwt.dto.AuthTokens;
import com.babpat.server.domain.member.dto.request.IdCheckRequestDto;
import com.babpat.server.domain.member.dto.request.SignInRequestDto;
import com.babpat.server.domain.member.dto.request.SignupRequestDto;
import com.babpat.server.domain.member.dto.response.IdCheckRespDto;
import com.babpat.server.domain.member.dto.response.ReissueResponse;
import com.babpat.server.domain.member.dto.response.SignInResponse;
import com.babpat.server.domain.member.dto.response.SignInResult;
import com.babpat.server.domain.member.service.auth.AuthService;
import com.babpat.server.domain.member.service.member.MemberQueryService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    @Value("${server.servlet.session.cookie.domain}")
    private String cookieDomain;

    private final AuthService authService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/duplicate")
    public ResponseEntity<ApiResponse<IdCheckRespDto>> checkIdExists(@RequestBody @Valid IdCheckRequestDto idCheckRequestDto) {
        IdCheckRespDto response = memberQueryService.isExistId(idCheckRequestDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(
                response,
                CustomResponseStatus.SUCCESS.withMessage("아이디 사용가능 여부 확인에 성공하였습니다."))
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody @Valid SignupRequestDto requestDto) {
        authService.register(requestDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(
                CustomResponseStatus.SUCCESS_WITH_NO_CONTENT.withMessage("회원가입 성공"))
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<SignInResponse>> login(
            @RequestBody @Valid SignInRequestDto signInRequestDto,
            HttpServletResponse response
    ) {
        SignInResult responseDto = authService.login(signInRequestDto);
        setRefreshTokenInCookie(response, responseDto.authTokens().refreshToken());

        return ResponseEntity.ok().body(ApiResponse.createSuccess(
                SignInResponse.of(responseDto),
                CustomResponseStatus.SUCCESS.withMessage("로그인 성공"))
        );
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<ReissueResponse>> reissue(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) {
        AuthTokens authTokens = authService.reissue(refreshToken);
        setRefreshTokenInCookie(response, authTokens.refreshToken());

        return ResponseEntity.ok().body(ApiResponse.createSuccess(
                new ReissueResponse(authTokens.accessToken()),
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

    private void setRefreshTokenInCookie(
            HttpServletResponse response,
            String refreshToken
    ) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setDomain(cookieDomain);
        response.addCookie(refreshTokenCookie);
    }
}
