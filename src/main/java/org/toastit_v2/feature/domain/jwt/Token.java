package org.toastit_v2.feature.domain.jwt;

import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.feature.domain.user.User;
import org.toastit_v2.feature.model.entity.jwt.TokenEntity;

@Getter
public class Token {

    private final Long userId;

    private String accessToken;

    private String refreshToken;

    private String grantType;

    @Builder
    public Token(Long userId, User user, String accessToken, String refreshToken, String grantType) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.grantType = grantType;
    }

    public void update(Token token) {
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
        this.grantType = token.getGrantType();
    }

    public TokenEntity toEntity() {
        return TokenEntity.builder()
                .userId(this.userId)
                .accessToken(this.accessToken)
                .refreshToken(this.refreshToken)
                .grantType(this.grantType)
                .build();
    }
}
