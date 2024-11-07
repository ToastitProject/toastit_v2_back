package org.toastit_v2.common.response.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.toastit_v2.common.response.code.ResponseCode;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {

    private final ResponseCode exceptionCode;

}
