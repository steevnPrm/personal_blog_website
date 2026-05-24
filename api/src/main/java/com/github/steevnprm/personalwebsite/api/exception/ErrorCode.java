package com.github.steevnprm.personalwebsite.api.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    SECTION_NOT_FOUND("SECTION.NOT_FOUND", HttpStatus.NOT_FOUND),
    STUDYCASE_NOT_FOUND("STUDYCASE.NOT_FOUND", HttpStatus.NOT_FOUND),
    INVALID_INPUT("VALIDATION.INVALID_INPUT", HttpStatus.BAD_REQUEST);

    private final String code;
    private final HttpStatus status;

    ErrorCode(String code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }

    public String getCode() { return code; }
    public HttpStatus getStatus() { return status; }
}