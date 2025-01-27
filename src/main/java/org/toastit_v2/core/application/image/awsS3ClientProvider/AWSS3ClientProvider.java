package org.toastit_v2.core.application.image.awsS3ClientProvider;

import com.amazonaws.services.s3.AmazonS3Client;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AWSS3ClientProvider {

    private final AmazonS3Client amazonS3Client;
    private final String bucketName;

    private final String tempFolder = "temporary/";

    public AWSS3ClientProvider(
            AmazonS3Client amazonS3Client,
            @NotNull @Value("${AWS_BUCKET_NAME}") String bucketName) {
        this.amazonS3Client = amazonS3Client;
        this.bucketName = bucketName;
    }
}
