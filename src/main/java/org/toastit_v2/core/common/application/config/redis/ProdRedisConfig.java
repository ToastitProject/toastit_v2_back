package org.toastit_v2.core.common.application.config.redis;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.validation.annotation.Validated;

@Profile("prod")
@Validated
@Configuration
public class ProdRedisConfig extends RedisConnectionFactoryConfig {

    private final String host;
    private final Integer port;

    public ProdRedisConfig(
            @NotNull @Value("${spring.data.redis.host}") String host,
            @NotNull @Value("${spring.data.redis.port}") Integer port
    ) {
        this.host = host;
        this.port = port;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

}
