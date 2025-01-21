package org.toastit_v2.core.common.application.config.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("test")
@PropertySource("classpath:properties/test-env.properties")
public class TestPropertyConfig {
}
