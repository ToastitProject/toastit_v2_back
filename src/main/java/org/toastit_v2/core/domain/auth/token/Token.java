package org.toastit_v2.core.domain.auth.token;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.toastit_v2.common.exception.custom.CustomTokenException;
import org.toastit_v2.common.response.code.ExceptionCode;

import java.util.Objects;

@RedisHash(
        value = "token",
        timeToLive = 4 * 60 * 60
)
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {

    @Id
    private String userId;

    @Column(nullable = false)
    private String accessToken;

    @Builder
    private Token(final String userId, final String accessToken) {
        notNullParameters(userId, accessToken);
        this.userId = userId;
        this.accessToken = accessToken;
    }

    private void notNullParameters(final String userId, final String accessToken) {
        if (Objects.isNull(userId) || Objects.isNull(accessToken)) {
            throw new CustomTokenException(ExceptionCode.TOKEN_PROCESSING_ERROR);
        }
    }

    public static Token create(final String userId, final String accessToken) {
        return new Token(userId, accessToken);
    }

}
