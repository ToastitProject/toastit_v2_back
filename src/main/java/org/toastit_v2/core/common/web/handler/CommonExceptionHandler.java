package org.toastit_v2.core.common.web.handler;

import lombok.extern.slf4j.Slf4j;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.exception.RestApiException;
import org.toastit_v2.core.common.util.response.ExceptionResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "org.toastit_v2")
public class CommonExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleRestApiException(RestApiException e) {
        log.error("RestApiException : {}", e.getExceptionCode().getData(), e);
        return ExceptionResponseUtil.handleResponseForException(e.getExceptionCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return ExceptionResponseUtil.handleResponseForException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException : {}", e.getMessage(), e);
        return ExceptionResponseUtil.handleResponseForException(CommonExceptionCode.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Object> handleUnsupportedOperationException(UnsupportedOperationException e) {
        log.error("UnsupportedOperationException: {}", e.getMessage(), e);
        return ExceptionResponseUtil.handleResponseForException(CommonExceptionCode.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
    }

}
