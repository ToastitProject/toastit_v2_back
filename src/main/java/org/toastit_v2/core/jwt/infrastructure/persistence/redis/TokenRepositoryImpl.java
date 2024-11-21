package org.toastit_v2.core.jwt.infrastructure.persistence.redis;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.toastit_v2.core.jwt.application.port.TokenRepository;

@Validated
@Repository
public class TokenRepositoryImpl implements TokenRepository {

    private final Long refreshTtl;
    private final RedisTemplate<String, Object> redisTemplate;

    public TokenRepositoryImpl(
            @NotNull @Value("${jwt.refresh.ttl.seconds}") Long refreshTtl,
            RedisTemplate<String, Object> redisTemplate
    ) {
        this.redisTemplate = redisTemplate;
        this.refreshTtl = refreshTtl;
    }

    public Optional<String> findById(String userId) {
        String value = (String) redisTemplate.opsForValue().get(userId);
        return Optional.ofNullable(value);
    }

    public void save(String userId, String refreshToken) {
        redisTemplate.opsForValue().set(userId, refreshToken, refreshTtl, TimeUnit.SECONDS);
    }

    public void deleteById(String userId) {
        redisTemplate.delete(userId);
    }

}
