package org.toastit_v2.feature.user.web.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;
import lombok.Getter;

@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserJoinRequest {

    @Schema(description = "사용자 이메일", example = "example@gmail.com")
    @Pattern(
            regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
            message = "이메일 형식으로 입력해주세요."
    )
    @NotBlank(message = "이메일을 입력해주세요.")
    private final String email;

    @Schema(description = "비밀번호", example = "ExamPass123!!")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$",
            message = "영어, 숫자, 특수문자를 포함한 8~16자 비밀번호를 입력해주세요."
    )
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;

    @Schema(description = "비밀번호 확인", example = "ExamPass123!!")
    @NotEmpty(message = "비밀번호 확인을 입력해주세요.")
    private final String passwordCheck;

    @Schema(description = "이메일 인증코드", example = "bct76t")
    @NotEmpty(message = "이메일을 인증해주세요.")
    private final String authCode;

    /**
     * @return 비밀번호와 비밀번호 확인 값이 동일하면 true
     */
    @Schema(hidden = true)
    @AssertTrue(message = "비밀번호와 비밀번호 확인이 일치하지 않습니다.")
    public boolean isPasswordValid() {
        return Objects.equals(password, passwordCheck);
    }

    @JsonCreator
    public UserJoinRequest(String email, String password, String passwordCheck, String authCode) {
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.authCode = authCode;
    }

}
