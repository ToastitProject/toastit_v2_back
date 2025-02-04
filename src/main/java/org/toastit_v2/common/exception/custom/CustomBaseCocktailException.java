package org.toastit_v2.common.exception.custom;

import lombok.Getter;
import org.toastit_v2.common.response.code.ExceptionCode;

@Getter
public class CustomBaseCocktailException extends RuntimeException{

    private final ExceptionCode code;

    public CustomBaseCocktailException(final ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

}
