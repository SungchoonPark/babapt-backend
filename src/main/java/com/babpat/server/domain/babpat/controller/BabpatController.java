package com.babpat.server.domain.babpat.controller;

import com.babpat.server.config.security.member.PrincipalDetails;
import com.babpat.server.domain.babpat.dto.request.BabpatApplyRequest;
import com.babpat.server.domain.babpat.dto.request.BabpatPostReqDto;
import com.babpat.server.domain.babpat.dto.request.SearchCond;
import com.babpat.server.domain.babpat.dto.response.BabpatInfoRespDto;
import com.babpat.server.common.dto.ApiResponse;
import com.babpat.server.common.enums.CustomResponseStatus;
import com.babpat.server.domain.babpat.service.babpat.BabpatCommandService;
import com.babpat.server.domain.babpat.service.babpat.BabpatQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/babpat")
@RequiredArgsConstructor
@Slf4j
public class BabpatController {
    private final BabpatQueryService babpatQueryService;
    private final BabpatCommandService babpatCommandService;

    @GetMapping("/post")
    public ResponseEntity<ApiResponse<Page<BabpatInfoRespDto>>> getBabpat(
            @ModelAttribute SearchCond searchCond,
            @PageableDefault(size = 12) Pageable pageable
    ) {
        Page<BabpatInfoRespDto> response = babpatQueryService.getBabpatWithPaging(searchCond, pageable);

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/post")
    public ResponseEntity<ApiResponse<Void>> postBabpat(
            @RequestBody @Valid BabpatPostReqDto babpatPostReqDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        babpatCommandService.postBabpat(babpatPostReqDto, principalDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS_WITH_NO_CONTENT));
    }

    @PostMapping("/post/apply")
    public ResponseEntity<ApiResponse<Void>> applyBabpat(
            @RequestBody @Valid BabpatApplyRequest applyRequest,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        babpatCommandService.applyBabpat(applyRequest, principalDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS_WITH_NO_CONTENT.withMessage("밥팟 신청이 완료되었습니다.")));
    }

    @DeleteMapping("/post/{babpatId}")
    public ResponseEntity<ApiResponse<Void>> applyBabpat(
            @PathVariable Long babpatId,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {
        babpatCommandService.delete(babpatId, principalDetails.getUsername());

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS_WITH_NO_CONTENT.withMessage("밥팟이 삭제되었습니다.")));
    }

}
