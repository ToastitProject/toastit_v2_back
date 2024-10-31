package org.example.toastit_v2.common.infra.config.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
public class AwsConfig {

    private final String accessKey;

    private final String secretKey;

    private final String region;

    public AwsConfig(
            @NotNull @Value("${cloud.aws.credentials.accessKey}") String accessKey,
            @NotNull @Value("${cloud.aws.credentials.secretKey}") String secretKey,
            @NotNull @Value("${cloud.aws.region.static}") String regin
    ) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = regin;
    }

    @Bean
    public AmazonS3Client amazonS3Client() {

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}