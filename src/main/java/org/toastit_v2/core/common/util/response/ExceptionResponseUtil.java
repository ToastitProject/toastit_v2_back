package org.toastit_v2.core.common.util.response;

import org.toastit_v2.core.common.web.response.ExceptionResponse;
import org.toastit_v2.core.common.application.code.ResponseCode;
import org.springframework.http.ResponseEntity;

public class ExceptionResponseUtil {

    private ExceptionResponseUtil() {
    }

    public static ResponseEntity<Object> handleResponseForException(final ResponseCode exceptionCode) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(buildErrorResponse(exceptionCode));
    }

    public static ResponseEntity<Object> handleResponseForException(final ResponseCode exceptionCode, final String message) {
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(buildErrorResponse(exceptionCode, message));
    }

    private static ExceptionResponse buildErrorResponse(final ResponseCode exceptionCode) {
        return ExceptionResponse.builder()
                .status(exceptionCode.getHttpStatus().toString())
                .code(exceptionCode.name())
                .data(exceptionCode.getData())
                .build();
    }

    private static ExceptionResponse buildErrorResponse(final ResponseCode exceptionCode, final String message) {
        return ExceptionResponse.builder()
                .status(exceptionCode.getHttpStatus().toString())
                .code(exceptionCode.name())
                .data(message)
                .build();
    }

}