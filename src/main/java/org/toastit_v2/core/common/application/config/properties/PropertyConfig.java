package org.toastit_v2.core.common.application.config.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:properties/dev-env.properties")
public class PropertyConfig {
}
