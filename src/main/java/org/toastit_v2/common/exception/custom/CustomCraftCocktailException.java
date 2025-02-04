package org.toastit_v2.common.exception.custom;

import lombok.Getter;
import org.toastit_v2.common.response.code.ExceptionCode;

@Getter
public class CustomCraftCocktailException extends RuntimeException {

    private final ExceptionCode code;

    public CustomCraftCocktailException(final ExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

}
