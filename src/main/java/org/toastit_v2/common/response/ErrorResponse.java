package org.toastit_v2.common.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.toastit_v2.common.response.code.ErrorCode;

@Getter
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ErrorResponse {

    private HttpStatus status;
    private int resultCode;
    private String resultMessage;
    private Object result;

    private ErrorResponse(final ErrorCode code, final Object result) {
        this.status = code.getHttpStatus();
        this.resultCode = code.getResultCode();
        this.resultMessage = code.getMessage();
        this.result = result;
    }

    public static ErrorResponse of(final ErrorCode code, final String reason) {
        return new ErrorResponse(code, reason);
    }

}
