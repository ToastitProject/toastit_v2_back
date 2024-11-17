package org.toastit_v2.core.common.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.toastit_v2.core.common.application.code.ResponseCode;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {

    private final ResponseCode exceptionCode;

}
