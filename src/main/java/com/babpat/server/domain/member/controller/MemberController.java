package com.babpat.server.domain.member.controller;

import com.babpat.server.common.dto.ApiResponse;
import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.config.security.member.PrincipalDetails;
import com.babpat.server.domain.member.dto.request.IdCheckRequestDto;
import com.babpat.server.domain.member.dto.response.IdCheckRespDto;
import com.babpat.server.domain.member.dto.response.MemberInfoResponse;
import com.babpat.server.domain.member.service.member.MemberQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberQueryService memberQueryService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberInfoResponse>> checkIdExists(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        MemberInfoResponse response = memberQueryService.getMemberInfo(principalDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccess(
                response,
                CustomResponseStatus.SUCCESS.withMessage("유저 정보 조회에 성공하였습니다."))
        );
    }

}
