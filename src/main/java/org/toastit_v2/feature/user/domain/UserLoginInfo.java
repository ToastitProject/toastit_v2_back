package org.toastit_v2.feature.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginInfo {

    private final Long userId;
    private final String username;
    private final String accessToken;
    private final String refreshToken;

    @Builder
    public UserLoginInfo(long userId, String username, String accessToken, String refreshToken) {
        this.userId = userId;
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static UserLoginInfo from(long userId, String username, String accessToken, String refreshToken) {
        return UserLoginInfo.builder()
                .userId(userId)
                .username(username)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
