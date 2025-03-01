package org.toastit_v2.common.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.toastit_v2.common.config.ssh.SshBridgeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DevDataConfig extends RedisConnectionFactoryConfig {

    private final SshBridgeConfig sshBridgeConfig;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        Integer forwardedPort = sshBridgeConfig.buildSshConnection(redisProperties.getHost(), redisProperties.getPort());

        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName("localhost");
        standaloneConfiguration.setPort(forwardedPort);

        return new LettuceConnectionFactory(standaloneConfiguration);
    }

}
