package org.toastit_v2.common.response.handler.util;

import org.toastit_v2.common.response.ExceptionResponse;
import org.toastit_v2.common.response.code.ResponseCode;
import org.springframework.http.ResponseEntity;

public class ExceptionResponseUtil {

    private ExceptionResponseUtil() {}

    public static ResponseEntity<Object> handleResponseForException(final ResponseCode exceptionCode) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(buildErrorResponse(exceptionCode));
    }

    private static ExceptionResponse buildErrorResponse(final ResponseCode exceptionCode) {
        return ExceptionResponse.builder()
                .status(exceptionCode.getHttpStatus().toString())
                .code(exceptionCode.name())
                .data(exceptionCode.getData())
                .build();
    }
}