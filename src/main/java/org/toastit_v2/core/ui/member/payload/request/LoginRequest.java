package org.toastit_v2.core.ui.member.payload.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(
        description = "로그인 요청 Request"
)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record LoginRequest(
        @NotNull(message = "회원 아이디는 필수 값 입니다.")
        @Schema(
                description = "회원 아이디",
                example = "test0328"
        )
        String userId,
        @NotBlank(message = "패스워드는 필수 값 입니다,")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$",
                message = "올바르지 않는 비밀번호 입니다."
        )
        @Schema(
                description = "패스워드",
                example = "@rowing0328"
        )
        String password
) {
}
