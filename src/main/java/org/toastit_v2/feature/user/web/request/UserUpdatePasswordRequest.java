package org.toastit_v2.feature.user.web.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserUpdatePasswordRequest {

    @Schema(
            description = "비밀번호",
            example = "ExamPass123!!"
    )
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$",
            message = "올바르지 않은 비밀번호 형식입니다."
    )
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private final String password;

    @JsonCreator
    public UserUpdatePasswordRequest(String password) {
        this.password = password;
    }

}
