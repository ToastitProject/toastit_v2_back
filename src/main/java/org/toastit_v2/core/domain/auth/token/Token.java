package org.toastit_v2.core.domain.auth.token;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

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

    private Token(final String userId, final String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public static Token create(final String userId, final String accessToken) {
        return new Token(userId, accessToken);
    }

}
