package com.github.steevnprm.personalwebsite.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ProblemDetail> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        ErrorCode errorCode = ex.getErrorCode();
        
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
            errorCode.getStatus(), 
            errorCode.getCode() 
        );
        
        problemDetail.setType(URI.create("https://api.personalwebsite.com/probs/" + errorCode.name().toLowerCase()));
        problemDetail.setTitle("Business Rule Violation");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        
        return ResponseEntity.of(problemDetail).build();
    }
}