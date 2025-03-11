package com.babpat.server.domain.member.controller;

import com.babpat.server.common.dto.ApiResponse;
import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.domain.member.dto.request.IdCheckRequestDto;
import com.babpat.server.domain.member.dto.response.IdCheckRespDto;
import com.babpat.server.domain.member.service.member.MemberQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberQueryService memberQueryService;

    @PostMapping("/duplicate")
    public ResponseEntity<ApiResponse<IdCheckRespDto>> checkIdExists(@RequestBody @Valid IdCheckRequestDto idCheckRequestDto) {
        IdCheckRespDto response = memberQueryService.isExistId(idCheckRequestDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(
                response,
                CustomResponseStatus.SUCCESS.withMessage("아이디 사용가능 여부 확인에 성공하였습니다."))
        );
    }
}
