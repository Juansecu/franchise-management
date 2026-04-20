package com.juansecu.franchisemanagement.infrastructure.delivery.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.juansecu.franchisemanagement.application.exceptions.HttpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.juansecu.franchisemanagement.infrastructure.delivery.dtos.responses.BaseResponse;

@RestControllerAdvice
public class HttpExceptionHandler {
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<BaseResponse<Object>> handleHttpException(
        final HttpException exception
    ) {
        final BaseResponse<Object> error = exception.getResponse() != null
            ? exception.getResponse()
            : BaseResponse.builder()
              .data(null)
              .errorCode(exception.getResponse().getErrorCode())
              .message(exception.getMessage())
              .success(false)
              .build();

        return ResponseEntity.status(exception.getStatusCode()).body(error);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
        final WebExchangeBindException ex
    ) {
        final Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
