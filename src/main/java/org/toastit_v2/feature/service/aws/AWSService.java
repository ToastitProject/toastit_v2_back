package org.toastit_v2.feature.service.aws;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AWSService {

    private final AmazonS3 s3Client;

    private final String bucketName;

    private final String filePath;

    public AWSService(
            AmazonS3 s3Client,
            @Value("${cloud.aws.s3.bucket}") String bucketName,
            @Value("${cloud.aws.cloudfront-path}") String filePath
    ) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.filePath = filePath;
    }
}