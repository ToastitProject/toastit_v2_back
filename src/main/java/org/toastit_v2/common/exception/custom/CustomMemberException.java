package org.toastit_v2.common.exception.custom;

import lombok.Getter;
import org.toastit_v2.common.response.code.ExceptionCode;

@Getter
public class CustomMemberException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public CustomMemberException(final ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}
