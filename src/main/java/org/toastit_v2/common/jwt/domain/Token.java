package org.toastit_v2.common.jwt.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Token {

    private final Long userId;
    private final String username;
    private final String accessToken;
    private final String refreshToken;

    @Builder
    public Token(Long userId, String username, String accessToken, String refreshToken) {
        this.userId = userId;
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static Token from(Long userId, String username, String accessToken, String refreshToken) {
        return Token.builder()
                .userId(userId)
                .username(username)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
