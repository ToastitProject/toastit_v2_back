package org.toastit_v2.common.exception.custom;

import lombok.Getter;
import org.toastit_v2.common.response.code.ExceptionCode;

@Getter
public class CustomSshBridgeException extends RuntimeException {

    private final ExceptionCode code;

    public CustomSshBridgeException(final ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

}
