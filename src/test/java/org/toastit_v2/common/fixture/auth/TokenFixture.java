package org.toastit_v2.common.fixture.auth;

import org.toastit_v2.core.domain.auth.token.Token;

public class TokenFixture {

    public static final String DEFAULT_USER_ID = "rowing0328";
    public static final String DEFAULT_TOKEN = "AYIDpalAMvEvQddKQXKoxO9OUc67N71r";
    public static final String BEARER_TOKEN = "Bearer " + DEFAULT_TOKEN;
    public static final String INVALID_TOKEN = "유효하지 않은 토큰";

    private TokenFixture() {
    }

    public static Token token(final String id, final String token) {
        return Token.builder()
                .userId(id)
                .accessToken(token)
                .build();
    }

    public static Token token() {
        return token(DEFAULT_USER_ID, DEFAULT_TOKEN);
    }

}
