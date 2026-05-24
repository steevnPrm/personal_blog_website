package com.github.steevnprm.personalwebsite.api.exception;

import java.util.Collections;
import java.util.Map;

public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Map<String, Object> params;

    public BusinessException(ErrorCode errorCode) {
        this(errorCode, Collections.emptyMap());
    }

    public BusinessException(ErrorCode errorCode, Map<String, Object> params) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
        this.params = params;
    }

    public ErrorCode getErrorCode() { return errorCode; }
    public Map<String, Object> getParams() { return params; }
}