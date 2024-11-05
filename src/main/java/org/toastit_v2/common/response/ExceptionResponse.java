package org.toastit_v2.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionResponse {

    private final String status;

    private final String code;

    private final Object data;

    @Builder
    public ExceptionResponse(String status, String code, Object data) {
        this.status = status;
        this.code = code;
        this.data = data;
    }
}
