package com.babpat.server.domain.settlement.controller;

import com.babpat.server.common.dto.ApiResponse;
import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.config.security.member.PrincipalDetails;
import com.babpat.server.domain.settlement.dto.request.PostSettlementRequest;
import com.babpat.server.domain.settlement.dto.response.AlarmResponse;
import com.babpat.server.domain.settlement.dto.response.SettlementInfo;
import com.babpat.server.domain.settlement.service.SettlementCommandService;
import com.babpat.server.domain.settlement.service.SettlementQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping()
    public ResponseEntity<ApiResponse<Page<SettlementInfo>>> getSettlements(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<SettlementInfo> response = queryService.getSettlementStates(principalDetails.getUsername(), pageable);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS.withMessage("정산 현황 조회에 성공하였습니다.")));
    }

    // 밥팟 알림 불러오기
    @GetMapping("/alarms")
    public ResponseEntity<ApiResponse<List<AlarmResponse>>> getSettlementAlarm(
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        List<AlarmResponse> response = queryService.getSettlementAlarms(principalDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS.withMessage("정산 알람 조회에 성공하였습니다.")));
    }

    // 돈보내기 API
    @PatchMapping("/{settlementId}")
    public ResponseEntity<ApiResponse<Page<SettlementInfo>>> getSettlements(
            @PathVariable Long settlementId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        commandService.payComplete(settlementId, principalDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS.withMessage("정산 처리 완료되었습니다.")));
    }
}
