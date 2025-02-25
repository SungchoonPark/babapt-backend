package com.babpat.server.member.controller;

import com.babpat.server.common.dto.ApiResponse;
import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.member.dto.request.IdCheckRequestDto;
import com.babpat.server.member.dto.request.SignInRequestDto;
import com.babpat.server.member.dto.request.SignupRequestDto;
import com.babpat.server.member.dto.response.IdCheckRespDto;
import com.babpat.server.member.dto.response.SignInResponseDto;
import com.babpat.server.member.service.MemberService;
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
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody SignupRequestDto requestDto) {
        memberService.register(requestDto);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<SignInResponseDto>> login(@RequestBody SignInRequestDto signInRequestDto) {
        SignInResponseDto response = memberService.login(signInRequestDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/duplicate")
    public ResponseEntity<ApiResponse<IdCheckRespDto>> checkIdExists(@RequestBody IdCheckRequestDto idCheckRequestDto) {
        IdCheckRespDto response = memberService.isExistId(idCheckRequestDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(
                response,
                CustomResponseStatus.SUCCESS.withMessage("아이디 사용가능 여부 확인에 성공하였습니다."))
        );
    }
}
