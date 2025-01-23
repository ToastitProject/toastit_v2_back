package org.toastit_v2.common.exception.custom;

import lombok.Getter;
import org.toastit_v2.common.response.code.ErrorCode;

@Getter
public class CustomMemberException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomMemberException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
