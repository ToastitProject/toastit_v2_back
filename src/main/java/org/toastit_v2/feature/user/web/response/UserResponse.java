package org.toastit_v2.feature.user.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.feature.user.domain.User;

@Getter
public class UserResponse {

    private String email;

    private String nickname;

    @JsonProperty("image_url")
    private String imageUrl;

    @Builder
    public UserResponse(String email, String nickname, String imageUrl) {
        this.email = email;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .build();
    }

}
