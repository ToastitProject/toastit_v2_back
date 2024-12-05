package org.toastit_v2.core.common.infrastructure.aws;

import com.amazonaws.services.s3.AmazonS3;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.toastit_v2.core.common.application.port.AWSS3FileHandler;

@Service
@Validated
public class AWSS3FileHandlerImpl implements AWSS3FileHandler {

    private final String bucketName;
    private final String filePath;

    private final AmazonS3 s3Client;

    public AWSS3FileHandlerImpl(
            @NotNull @Value("${cloud.aws.s3.bucket}") String bucketName,
            @NotNull @Value("${cloud.aws.cloudfront-path}") String filePath,
            AmazonS3 s3Client
    ) {
        this.bucketName = bucketName;
        this.filePath = filePath;
        this.s3Client = s3Client;
    }

}