package org.toastit_v2.core.jwt.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.core.jwt.domain.Token;

@Getter
public class TokenResponse {

    private final Long userId;
    private final String username;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @Builder
    public TokenResponse(long userId, String username, String accessToken, String refreshToken) {
        this.userId = userId;
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenResponse from(Token token) {
        return TokenResponse.builder()
                .userId(token.getUserId())
                .username(token.getUsername())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build();
    }

}
