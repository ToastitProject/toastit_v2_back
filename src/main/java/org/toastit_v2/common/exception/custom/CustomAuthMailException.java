package org.toastit_v2.common.exception.custom;

import lombok.Getter;
import org.toastit_v2.common.response.code.ExceptionCode;

@Getter
public class CustomAuthMailException extends RuntimeException {

    private final ExceptionCode code;

    public CustomAuthMailException(final ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.code = exceptionCode;
    }

}
