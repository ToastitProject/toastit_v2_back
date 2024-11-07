package org.toastit_v2.common.response.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonResponseCode implements ResponseCode {

    SUCCESS(HttpStatus.OK, "요청이 성공됐습니다"),

    CREATED(HttpStatus.CREATED, "리소스가 성공적으로 생성되었습니다"),

    ACCEPTED(HttpStatus.ACCEPTED, "요청이 접수되었습니다"),

    NO_CONTENT(HttpStatus.NO_CONTENT, "요청이 성공적으로 처리되었으나 반환할 내용이 없습니다"),

    RESET_CONTENT(HttpStatus.RESET_CONTENT, "컨텐츠를 초기화하십시오"),

    PARTIAL_CONTENT(HttpStatus.PARTIAL_CONTENT, "부분적인 내용이 반환되었습니다");

    private final HttpStatus httpStatus;

    private final String data;

    CommonResponseCode(HttpStatus httpStatus, String data) {
        this.httpStatus = httpStatus;
        this.data = data;
    }

}
