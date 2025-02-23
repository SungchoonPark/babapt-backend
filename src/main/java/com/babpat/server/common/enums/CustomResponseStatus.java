package com.babpat.server.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomResponseStatus {
    SUCCESS(HttpStatus.OK.value(), "요청에 성공하였습니다."),
    SUCCESS_WITH_NO_CONTENT(HttpStatus.NO_CONTENT.value() , "요청에 성공하였습니다.");

    private final int httpStatusCode;
    private final String message;

    CustomResponseStatus(int httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
