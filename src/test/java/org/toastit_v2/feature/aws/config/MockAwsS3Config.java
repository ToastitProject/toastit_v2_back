package org.toastit_v2.feature.aws.config;

import com.amazonaws.services.s3.AmazonS3;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.toastit_v2.core.common.application.config.aws.AwsConfig;


@Configuration
public class MockAwsS3Config extends AwsConfig {


    public MockAwsS3Config(String accessKey, String secretKey, String regin) {
        super(accessKey, secretKey, regin);
    }

    @Bean
    @Override
    public AmazonS3 amazonS3() {
        return Mockito.mock(AmazonS3.class);
    }
}
