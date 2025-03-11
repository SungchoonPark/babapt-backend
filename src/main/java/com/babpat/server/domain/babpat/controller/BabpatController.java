package com.babpat.server.domain.babpat.controller;

import com.babpat.server.domain.babpat.dto.request.BabpatApplyRequest;
import com.babpat.server.domain.babpat.dto.request.BabpatPostReqDto;
import com.babpat.server.domain.babpat.dto.response.BabpatInfoRespDto;
import com.babpat.server.domain.babpat.service.BabpatMainService;
import com.babpat.server.common.dto.ApiResponse;
import com.babpat.server.common.enums.CustomResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/babpat")
@RequiredArgsConstructor
@Slf4j
public class BabpatController {
    private final BabpatMainService babpatMainService;

    @PostMapping("/post")
    public ResponseEntity<ApiResponse<Void>> postBabpat(@RequestBody @Valid BabpatPostReqDto babpatPostReqDto) {
        babpatMainService.postBabpat(babpatPostReqDto);


        System.out.println("test");
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS_WITH_NO_CONTENT));
    }

    @GetMapping("/post")
    public ResponseEntity<ApiResponse<BabpatInfoRespDto>> getBabpat() {
        BabpatInfoRespDto response = babpatMainService.getBabpat();

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @PostMapping("/post/apply")
    public ResponseEntity<ApiResponse<Void>> applyBabpat(@RequestBody @Valid BabpatApplyRequest applyRequest) {
        babpatMainService.applyBabpat(applyRequest);

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS_WITH_NO_CONTENT.withMessage("밥팟 신청이 완료되었습니다.")));
    }

}
