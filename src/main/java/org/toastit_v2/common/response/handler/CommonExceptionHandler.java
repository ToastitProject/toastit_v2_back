package org.toastit_v2.common.response.handler;

import lombok.extern.slf4j.Slf4j;
import org.toastit_v2.common.response.exception.RestApiException;
import org.toastit_v2.common.response.handler.util.ExceptionResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "org.toastit_v2")
public class CommonExceptionHandler {

    // RestApiException 예외 처리 핸들러
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleRestApiException(RestApiException e) {
        log.error("RestApiException : {}", e.getExceptionCode().getData(), e);
        return ExceptionResponseUtil.handleResponseForException(e.getExceptionCode());
    }
}
