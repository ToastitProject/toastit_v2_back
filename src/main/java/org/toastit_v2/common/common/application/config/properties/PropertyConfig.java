package org.toastit_v2.common.common.application.config.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:properties/${spring.profiles.active}-env.properties")
public class PropertyConfig {
}
