package org.toastit_v2.common.fixture.auth;

import org.toastit_v2.core.domain.auth.mail.AuthMail;

import java.time.LocalDateTime;

public class AuthMailFixture {

    public static final String DEFAULT_EMAIL = "dev.hyoseung@gmail.com";
    public static final String DEFAULT_AUTH_CODE = "123456";
    public static final LocalDateTime DEFAULT_CREATED_AT = LocalDateTime.of(2025, 1, 2, 12, 8, 0);

    private AuthMailFixture() {
    }

    private static AuthMail authmail(final String email, final String code, final LocalDateTime createdAt) {
        return AuthMail.builder()
                .userEmail(email)
                .authCode(code)
                .registerDate(createdAt)
                .build();
    }

    public static AuthMail authMail() {
        return authmail(DEFAULT_EMAIL, DEFAULT_AUTH_CODE, DEFAULT_CREATED_AT);
    }

}
