package org.toastit_v2.core.jwt.web.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TokenRefreshRequest {

    @Schema(
            description = "리프레시 토큰",
            example = " "
    )
    @NotBlank(message = "리프레시 토큰 값을 입력해주세요.")
    private final String refreshToken;

    @JsonCreator
    public TokenRefreshRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
