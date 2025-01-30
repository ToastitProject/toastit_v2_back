package org.toastit_v2.common.exception.custom;

import lombok.Getter;
import org.toastit_v2.common.response.code.ExceptionCode;

@Getter
public class CustomTrendCocktailException extends RuntimeException {

    private final ExceptionCode code;

    public CustomTrendCocktailException(final ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

}
