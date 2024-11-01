package org.toastit_v2.common.response.exception;

import lombok.Getter;
import org.toastit_v2.common.response.code.ResponseCode;

@Getter
public class RestApiException extends RuntimeException {

    private final ResponseCode exceptionCode;

    public RestApiException(ResponseCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}