package org.toastit_v2.common.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.toastit_v2.common.response.code.ExceptionCode;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExceptionResponse {

    private HttpStatus status;
    private int resultCode;
    private String resultMessage;
    private List<FieldError> errors;
    private Object result;

    public ExceptionResponse(final ExceptionCode code, final List<FieldError> errors, final Object result) {
        this.status = code.getHttpStatus();
        this.resultCode = code.getResultCode();
        this.resultMessage = code.getMessage();
        this.errors = errors;
        this.result = result;
    }

    protected ExceptionResponse(final List<FieldError> errors, final ExceptionCode code) {
        this(code, errors, null);
    }

    public static ExceptionResponse create(final ExceptionCode code, final String reason) {
        return new ExceptionResponse(code, null, reason);
    }

    public static ExceptionResponse ofFieldError(final BindingResult bindingResult, final ExceptionCode code) {
        return new ExceptionResponse(ExceptionResponse.FieldError.from(bindingResult), code);
    }

    @Getter
    public static class FieldError {

        private final String field;
        private final String value;
        private final String reason;

        public FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        private static List<ExceptionResponse.FieldError> from(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream().map(error -> new ExceptionResponse.FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }

    }

}
