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

    public static HttpException badRequest(final String message) {
        return HttpException.badRequest(message, "BadRequest", null);
    }

    public static HttpException badRequest(
        final String message,
        final String errorCode
    ) {
        return HttpException.badRequest(message, errorCode, null);
    }

    public static HttpException badRequest(
        final String message,
        final String errorCode,
        final BaseResponse<Object> response
    ) {
        final BaseResponse<Object> res = HttpException.getResponseBody(
            message,
            errorCode,
            response
        );

        return new HttpException(
            message,
            400,
            res
        );
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
        final BaseResponse<Object> res = HttpException.getResponseBody(
            message,
            errorCode,
            response
        );

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
        final BaseResponse<Object> res = HttpException.getResponseBody(
            message,
            errorCode,
            response
        );

        return new HttpException(
            message,
            404,
            res
        );
    }

    private static BaseResponse<Object> getResponseBody(
        final String message,
        final String errorCode,
        final BaseResponse<Object> response
    ) {
        return response != null
            ? response
            : BaseResponse.builder()
              .data(null)
              .errorCode(errorCode)
              .message(message)
              .success(false)
              .build();
    }
}
