package org.toastit_v2.feature.user.web.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UserUpdateNicknameRequest {

    @Schema(
            description = "닉네임",
            example = "뛰어난 1번째 사자"
    )
    @NotEmpty
    private final String nickname;

    @JsonCreator
    public UserUpdateNicknameRequest(String nickname) {
        this.nickname = nickname;
    }

}
