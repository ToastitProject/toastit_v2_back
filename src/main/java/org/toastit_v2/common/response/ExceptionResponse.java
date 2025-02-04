package org.toastit_v2.common.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.toastit_v2.common.response.code.ExceptionCode;

import java.util.List;

@Getter
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExceptionResponse {

    private Object result;
    private HttpStatus status;
    private String message;
    private int statusCode;
    private List<FieldError> errors;

    public ExceptionResponse(final ExceptionCode code, final List<FieldError> errors, final Object result) {
        this.status = code.getHttpStatus();
        this.statusCode = code.getStatusCode();
        this.message = code.getMessage();
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

    public record FieldError(String field, String value, String reason) {

        private static List<FieldError> from(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream().map(error -> new ExceptionResponse.FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .toList();
        }

    }

}
