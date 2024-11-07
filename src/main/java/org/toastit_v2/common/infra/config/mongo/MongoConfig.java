package org.toastit_v2.common.infra.config.mongo;

import com.mongodb.client.MongoClients;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
public class MongoConfig {

    private final String url;

    private final String database;

    public MongoConfig(
            @NotNull @Value("${spring.data.mongodb.uri}") String url,
            @NotNull @Value("${spring.data.mongodb.database}") String database
    ) {
        this.url = url;
        this.database = database;
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(MongoClients.create(url), database);
    }

}
