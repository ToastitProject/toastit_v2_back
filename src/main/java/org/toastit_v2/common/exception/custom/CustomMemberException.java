package org.toastit_v2.common.exception.custom;

import lombok.Getter;
import org.toastit_v2.common.response.code.ExceptionCode;

@Getter
public class CustomMemberException extends RuntimeException {

    private final ExceptionCode code;

    public CustomMemberException(final ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

}
