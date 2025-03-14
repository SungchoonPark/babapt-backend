package com.babpat.server.domain.settlement.controller;

import com.babpat.server.common.dto.ApiResponse;
import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.config.security.member.PrincipalDetails;
import com.babpat.server.domain.babpat.dto.request.BabpatPostReqDto;
import com.babpat.server.domain.settlement.dto.request.PostSettlementRequest;
import com.babpat.server.domain.settlement.service.SettlementCommandService;
import com.babpat.server.domain.settlement.service.SettlementQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/settlements")
@RequiredArgsConstructor
public class SettlementController {
    private final SettlementCommandService commandService;
    private final SettlementQueryService queryService;

    // 밥팟 정산 요청하기
    @PostMapping()
    public ResponseEntity<ApiResponse<Void>> postSettlement(
            @RequestBody @Valid PostSettlementRequest postSettlementRequest,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        commandService.postSettlement(postSettlementRequest, principalDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS_WITH_NO_CONTENT.withMessage("정산 요청이 완료되었습니다.")));
    }

    // 내 밥팟 정산 현황 조회

    // 밥팟 알림 불러오기
}
