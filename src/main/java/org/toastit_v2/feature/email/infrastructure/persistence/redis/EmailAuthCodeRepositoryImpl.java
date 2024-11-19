package org.toastit_v2.feature.email.infrastructure.persistence.redis;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.toastit_v2.feature.email.application.port.EmailAuthCodeRepository;

@Service
@RequiredArgsConstructor
public class EmailAuthCodeRepositoryImpl implements EmailAuthCodeRepository {

    private static final long AUTH_CODE_EXPIRATION = 10;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public String findByEmail(String email) {
        return (String) redisTemplate.opsForValue().get(email);
    }

    @Override
    public void save(String email, String code) {
        redisTemplate.opsForValue().set(email, code, AUTH_CODE_EXPIRATION, TimeUnit.MINUTES);
    }

    @Override
    public void delete(String email) {
        redisTemplate.delete(email);
    }

}
