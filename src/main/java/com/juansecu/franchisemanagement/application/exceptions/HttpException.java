package com.juansecu.franchisemanagement.application.exceptions;

import lombok.Getter;

import com.juansecu.franchisemanagement.infrastructure.delivery.dtos.responses.BaseResponse;

@Getter
public class HttpException extends RuntimeException {
    private final BaseResponse<Object> response;
    private final int statusCode;

    private HttpException(
        final String message,
        final int statusCode,
        final BaseResponse<Object> response
    ) {
        super(message);
        this.response = response;
        this.statusCode = statusCode;
    }

    public static HttpException conflict(final String message) {
        return HttpException.conflict(message, "Conflict", null);
    }

    public static HttpException conflict(
        final String message,
        final String errorCode
    ) {
        return HttpException.conflict(message, errorCode, null);
    }

    public static HttpException conflict(
        final String message,
        final String errorCode,
        final BaseResponse<Object> response
    ) {
        final BaseResponse<Object> res = response != null
            ? response
            : BaseResponse.builder()
              .data(null)
              .errorCode(errorCode)
              .message(message)
              .success(false)
              .build();

        return new HttpException(
            message,
            409,
            res
        );
    }

    public static HttpException notFound(final String message) {
        return HttpException.notFound(message, "NotFound", null);
    }

    public static HttpException notFound(
        final String message,
        final String errorCode
    ) {
        return HttpException.notFound(message, errorCode, null);
    }

    public static HttpException notFound(
        final String message,
        final String errorCode,
        final BaseResponse<Object> response
    ) {
        final BaseResponse<Object> res = response != null
            ? response
            : BaseResponse.builder()
              .data(null)
              .errorCode(errorCode)
              .message(message)
              .success(false)
              .build();

        return new HttpException(
            message,
            404,
            res
        );
    }
}
