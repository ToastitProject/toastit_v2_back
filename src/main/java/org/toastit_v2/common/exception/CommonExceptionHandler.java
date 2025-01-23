package org.toastit_v2.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.toastit_v2.common.exception.custom.CustomMemberException;
import org.toastit_v2.common.response.ErrorResponse;
import org.toastit_v2.common.response.code.ErrorCode;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(CustomMemberException.class)
    protected ResponseEntity<ErrorResponse> handleCustomMemberException(CustomMemberException ex) {
        log.error("Excepion",ex);
        return buildErrorResponse(ex);
    }

    private static <T extends RuntimeException> ResponseEntity<ErrorResponse> buildErrorResponse(final T ex) {
        ErrorCode errorCode = ErrorCode.getErrorResponseCode(ex.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, ex.getMessage() == null ? "empty" : ex.getMessage());
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

}
