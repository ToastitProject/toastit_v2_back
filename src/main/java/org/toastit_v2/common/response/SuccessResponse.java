package org.toastit_v2.common.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SuccessResponse<T> {

    private final T result;

    private final HttpStatus httpStatus;

    private final String message;

    private final int statusCode;

}
