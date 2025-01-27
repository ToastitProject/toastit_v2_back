package org.toastit_v2.common.exception.custom;

import org.toastit_v2.common.response.code.ExceptionCode;

public class CustomTokenException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public CustomTokenException(final ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}
