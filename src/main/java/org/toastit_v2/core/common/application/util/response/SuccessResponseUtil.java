package org.toastit_v2.core.common.application.util.response;

import org.toastit_v2.core.common.web.response.SuccessResponse;
import org.toastit_v2.core.common.application.code.ResponseCode;
import org.springframework.http.ResponseEntity;

public class SuccessResponseUtil {

    /**
     * 유틸리티 클래스이므로 인스턴스화 방지를 위한 private 생성자.
     */
    private SuccessResponseUtil() {
    }

    public static <T> ResponseEntity<Object> handleResponseForSuccess(final ResponseCode responseCode, T data) {
        return ResponseEntity.status(responseCode.getHttpStatus())
                .body(buildSuccessResponse(responseCode, data));
    }

    private static <T> SuccessResponse<T> buildSuccessResponse(final ResponseCode responseCode, T data) {
        return SuccessResponse.<T>builder()
                .status(responseCode.getHttpStatus().toString())
                .code(responseCode.name())
                .data(data)
                .msg(responseCode.getData())
                .build();
    }
}