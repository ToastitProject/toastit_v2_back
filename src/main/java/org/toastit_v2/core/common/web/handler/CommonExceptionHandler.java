package org.toastit_v2.core.common.web.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.exception.RestApiException;
import org.toastit_v2.core.common.util.response.ExceptionResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "org.toastit_v2")
public class CommonExceptionHandler {

    /**
     * RestApiException을 처리하는 핸들러
     *
     * @param e 처리할 {@link RestApiException}
     * @return 예외를 처리한 후 반환할 {@link ResponseEntity}
     */
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleRestApiException(RestApiException e) {
        log.error("RestApiException : {}", e.getExceptionCode().getData(), e);
        return ExceptionResponseUtil.handleResponseForException(e.getExceptionCode());
    }

    /**
     * 모든 예외를 처리하는 핸들러
     * <p>
     * 모든 예외가 처리되지 않았을 때 기본적으로 호출됩니다.
     * </p>
     *
     * @param e 처리할 {@link Exception}
     * @return 예외를 처리한 후 반환할 {@link ResponseEntity}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return ExceptionResponseUtil.handleResponseForException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 잘못된 인자가 전달된 예외를 처리하는 핸들러
     *
     * @param e 처리할 {@link IllegalArgumentException}
     * @return 예외를 처리한 후 반환할 {@link ResponseEntity}
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException : {}", e.getMessage(), e);
        return ExceptionResponseUtil.handleResponseForException(CommonExceptionCode.BAD_REQUEST, e.getMessage());
    }

    /**
     * 지원되지 않는 작업을 시도할 때 발생하는 예외를 처리하는 핸들러
     *
     * @param e 처리할 {@link UnsupportedOperationException}
     * @return 예외를 처리한 후 반환할 {@link ResponseEntity}
     */
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Object> handleUnsupportedOperationException(UnsupportedOperationException e) {
        log.error("UnsupportedOperationException: {}", e.getMessage(), e);
        return ExceptionResponseUtil.handleResponseForException(CommonExceptionCode.UNSUPPORTED_MEDIA_TYPE,
                e.getMessage());
    }

    /**
     * 다양한 검증 예외들을 처리하는 핸들러
     * <p>
     * 이 핸들러는 다음과 같은 예외를 처리합니다.
     * </p>
     * <ul>
     *     <li> {@link MethodArgumentNotValidException} : 유효하지 않은 메소드 인자</li>
     *     <li> {@link MissingServletRequestParameterException} : 누락된 요청 파라미터</li>
     *     <li> {@link MissingServletRequestPartException} : 누락된 요청 파트</li>
     *     <li> {@link HandlerMethodValidationException} : 핸들러 메소드 검증 실패</li>
     *     <li> {@link ConstraintViolationException} : 제약 조건 위반</li>
     * </ul>
     *
     * @param e 처리할 {@link Exception}
     * @return 예외 처리 후 반환할 {@link ResponseEntity}
     */
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class,
            HandlerMethodValidationException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<Object> handleValidationExceptions(Exception e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);

        if (e instanceof MethodArgumentNotValidException) {
            return ExceptionResponseUtil.handleResponseForException(CommonExceptionCode.FILE_ERROR, (MethodArgumentNotValidException) e);
        }

        return ExceptionResponseUtil.handleResponseForException(CommonExceptionCode.FILE_ERROR);
    }

}
