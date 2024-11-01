package org.toastit_v2.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class SuccessResponse<T> {

    // 응답 상태
    private final String status;

    // 응답 메시지
    private final String msg;

    // 응답 데이터
    private final T data;

    // 응답 코드
    private final String code;

    @Builder
    public SuccessResponse(String status, String msg, T data, String code) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.code = code;
    }
}
