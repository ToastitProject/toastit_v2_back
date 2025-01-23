package org.toastit_v2.common.response.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    SUCCESS(HttpStatus.OK, "요청이 성공하였습니다.", 200),

    CREATED(HttpStatus.CREATED, "리소스가 성공적으로 생성되었습니다.", 201),

    ACCEPTED(HttpStatus.ACCEPTED, "요청이 접수되었습니다.", 202),

    NO_CONTENT(HttpStatus.NO_CONTENT, "요청이 성공적으로 처리되었으나 반환할 내용이 없습니다.", 204),

    RESET_CONTENT(HttpStatus.RESET_CONTENT, "컨텐츠를 초기화하십시오.", 205),

    PARTIAL_CONTENT(HttpStatus.PARTIAL_CONTENT, "부분적인 내용이 반환되었습니다", 206);

    private final HttpStatus httpStatus;
    private final String message;
    private final int resultCode;

}
