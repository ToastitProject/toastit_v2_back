package org.toastit_v2.common.exception.custom;

import lombok.Getter;
import org.toastit_v2.common.response.code.ExceptionCode;

@Getter
public class CustomAWSException extends RuntimeException {

    private final ExceptionCode code;

    public CustomAWSException(final ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }


}
