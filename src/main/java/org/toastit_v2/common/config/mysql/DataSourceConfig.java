package org.toastit_v2.common.config.mysql;

import jakarta.validation.constraints.NotNull;
import javax.sql.DataSource;
import org.toastit_v2.common.config.ssh.SshBridgeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@Profile("dev")
public class DataSourceConfig {

    private final String databaseUrl;
    private final Integer databasePort;
    private final SshBridgeConfig initializer;

    public DataSourceConfig(
            @NotNull @Value("${cloud.aws.rds.database.url}") String databaseUrl,
            @NotNull @Value("${cloud.aws.rds.database.port}") Integer databasePort,
            SshBridgeConfig initializer
    ) {
        this.databaseUrl = databaseUrl;
        this.databasePort = databasePort;
        this.initializer = initializer;
    }

    @Primary
    @Bean
    public DataSource dataSource(DataSourceProperties properties) {
        Integer forwardedPort = initializer.buildSshConnection(databaseUrl, databasePort);
        String url = properties.getUrl().replace("[forwardedPort]", Integer.toString(forwardedPort));

        return DataSourceBuilder.create()
                .url(url)
                .username(properties.getUsername())
                .password(properties.getPassword())
                .driverClassName(properties.getDriverClassName())
                .build();
    }

}
