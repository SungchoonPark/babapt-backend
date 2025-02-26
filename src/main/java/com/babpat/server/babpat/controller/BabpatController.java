package com.babpat.server.babpat.controller;

import com.babpat.server.babpat.dto.request.BabpatPostReqDto;
import com.babpat.server.babpat.dto.response.BabpatInfoRespDto;
import com.babpat.server.babpat.service.BabpatMainService;
import com.babpat.server.common.dto.ApiResponse;
import com.babpat.server.common.enums.CustomResponseStatus;
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
    public ResponseEntity<ApiResponse<?>> postBabpat(@RequestBody BabpatPostReqDto babpatPostReqDto) {
        babpatMainService.postBabpat(babpatPostReqDto);

        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS_WITH_NO_CONTENT));
    }

    @GetMapping("/post")
    public ResponseEntity<ApiResponse<BabpatInfoRespDto>> getBabpat() {
        BabpatInfoRespDto response = babpatMainService.getBabpat();

        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

}
