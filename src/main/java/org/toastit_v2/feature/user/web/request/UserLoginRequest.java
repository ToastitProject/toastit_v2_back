package org.toastit_v2.feature.user.web.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserLoginRequest {

    @Schema(
            description = "이메일",
            example = "example@gmail.com"
    )
    @Pattern(
            regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
            message = "올바르지 않은 이메일 형식입니다."
    )
    @NotEmpty(message = "이메일을 입력해주세요.")
    private final String email;

    @Schema(
            description = "비밀번호",
            example = "ExamPass123!!"
    )
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$",
            message = "올바르지 않은 비밀번호 형식입니다."
    )
    @NotEmpty
    private final String password;

    @JsonCreator
    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
