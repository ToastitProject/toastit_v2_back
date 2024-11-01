package org.toastit_v2.common.infra.config.redis;

import jakarta.validation.constraints.NotNull;
import org.toastit_v2.common.infra.config.ssh.SshTunnelingInitializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.validation.annotation.Validated;

@Profile("dev")
@Validated
@Configuration
public class DevRedisConfig extends RedisConnectionFactoryConfig{

    private String host;

    private Integer port;

    private SshTunnelingInitializer sshTunnelingInitializer;

    public DevRedisConfig(
            @NotNull @Value("${spring.data.redis.host}") String host,
            @NotNull @Value("${spring.data.redis.port}") Integer port,
            SshTunnelingInitializer sshTunnelingInitializer
    ) {
        this.host = host;
        this.port = port;
        this.sshTunnelingInitializer = sshTunnelingInitializer;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        Integer forwardedPort = sshTunnelingInitializer.buildSshConnection(host, port);

        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName("localhost");
        standaloneConfiguration.setPort(forwardedPort);

        return new LettuceConnectionFactory(standaloneConfiguration);
    }
}