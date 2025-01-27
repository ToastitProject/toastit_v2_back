package org.toastit_v2.core.ui.auth.mail.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(
        description = "인증 메일 요청 Request"
)
public record AuthMailRequest(
        @JsonProperty("user_email")
        @Schema(
                description = "회원 메일",
                example = "test@naver.com"
        )
        @NotBlank(message = "회원 이메일은 필수 입니다.")
        String userEmail) {
}
