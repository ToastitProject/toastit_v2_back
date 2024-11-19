package org.toastit_v2.feature.user.web.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.feature.user.domain.UserLoginInfo;

@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserLoginResponse {

    private final long userId;
    private final String username;
    private final String accessToken;
    private final String refreshToken;

    @Builder
    public UserLoginResponse(long userId, String username, String accessToken, String refreshToken) {
        this.userId = userId;
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static UserLoginResponse from(UserLoginInfo info) {
        return UserLoginResponse.builder()
                .userId(info.getUserId())
                .username(info.getUsername())
                .accessToken(info.getAccessToken())
                .refreshToken(info.getRefreshToken())
                .build();
    }

}
