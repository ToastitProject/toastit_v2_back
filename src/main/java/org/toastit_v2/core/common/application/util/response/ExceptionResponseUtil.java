package org.toastit_v2.core.common.application.util.response;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.toastit_v2.core.common.web.response.ExceptionResponse;
import org.toastit_v2.core.common.application.code.ResponseCode;
import org.springframework.http.ResponseEntity;
import org.toastit_v2.core.common.web.response.ExceptionResponse.ValidationException;

public class ExceptionResponseUtil {

    /**
     * 유틸리티 클래스이므로 인스턴스화 방지를 위한 private 생성자.
     */
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

    public static ResponseEntity<Object> handleResponseForException(final ResponseCode exceptionCode, final MethodArgumentNotValidException exception
    ) {
        List<ValidationException> validationExceptions = from(exception);
        return ResponseEntity.status(exceptionCode.getHttpStatus())
                .body(buildErrorResponse(exceptionCode, validationExceptions));
    }

    private static List<ValidationException> from(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationException::fromFieldError)
                .collect(Collectors.toList());
    }

    private static ExceptionResponse buildErrorResponse(final ResponseCode exceptionCode, List<ValidationException> validationExceptions) {
        return ExceptionResponse.builder()
                .status(exceptionCode.getHttpStatus().toString())
                .code(exceptionCode.name())
                .data(validationExceptions)
                .build();
    }

    private static ExceptionResponse buildErrorResponse(final ResponseCode exceptionCode, final String message) {
        return ExceptionResponse.builder()
                .status(exceptionCode.getHttpStatus().toString())
                .code(exceptionCode.name())
                .data(message)
                .build();
    }

    private static ExceptionResponse buildErrorResponse(final ResponseCode exceptionCode) {
        return ExceptionResponse.builder()
                .status(exceptionCode.getHttpStatus().toString())
                .code(exceptionCode.name())
                .data(exceptionCode.getData())
                .build();
    }

}