package com.babpat.server;

import com.babpat.server.common.dto.ApiResponse;
import com.babpat.server.common.enums.CustomResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class SystemController {
    @GetMapping("/")
    public ResponseEntity<ApiResponse<Void>> getSuccess() {
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS));
    }

    @GetMapping({"/favicon.ico", "/.env"})
    public ResponseEntity<ApiResponse<Void>> ignoreRequests() {
        // 아무 내용 없이 요청을 무시
        return ResponseEntity.ok().body(ApiResponse.createSuccessWithNoContent(CustomResponseStatus.SUCCESS_WITH_NO_CONTENT));
    }
}
