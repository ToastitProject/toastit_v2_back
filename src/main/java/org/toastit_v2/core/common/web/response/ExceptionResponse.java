package org.toastit_v2.core.common.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
@Builder
public class ExceptionResponse {

    private final String status;

    private final String code;

    @JsonInclude(Include.NON_EMPTY)
    private final Object data;

    @JsonInclude(Include.NON_EMPTY)
    private final List<ValidationException> errors;

    @Getter
    public static class ValidationException {

        private final String field;

        private final Object data;

        @Builder
        public ValidationException(String field, Object data) {
            this.field = field;
            this.data = data;
        }

        public static ValidationException fromFieldError(final FieldError fieldError) {
            return ValidationException.builder()
                    .field(fieldError.getField())
                    .data(fieldError.getDefaultMessage())
                    .build();

        }

    }

}
