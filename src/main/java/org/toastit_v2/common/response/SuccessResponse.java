package org.toastit_v2.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class SuccessResponse<T> {

    private final String status;

    private final String msg;

    private final T data;

    private final String code;

    @Builder
    public SuccessResponse(String status, String msg, T data, String code) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.code = code;
    }

}
