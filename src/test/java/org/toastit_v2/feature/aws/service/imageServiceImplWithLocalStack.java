package org.toastit_v2.feature.aws.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.toastit_v2.common.common.application.code.CommonExceptionCode;
import org.toastit_v2.common.common.application.exception.RestApiException;
import org.toastit_v2.feature.aws.application.service.ImageService;
import java.io.IOException;

@Component
@Qualifier("test")
public class imageServiceImplWithLocalStack implements ImageService {

    private final AmazonS3Client amazonS3Client;

    private final String bucketName;

    private final String tempFolder = "temporary/";

    public org.toastit_v2.feature.aws.application.util.FileUtil FileUtil;


    public imageServiceImplWithLocalStack(
            AmazonS3Client amazonS3Client,
            @Value("${AWS_TEST_BUCKET_NAME}") String bucketName) {
        this.amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566","us-east-1"))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("test","test")))
                .enablePathStyleAccess()
                .build();
        this.bucketName = bucketName;
        if (!amazonS3Client.doesBucketExistV2(bucketName)) {
            amazonS3Client.createBucket(bucketName);
        }
    }

    @Override
    public String uploadFile(MultipartFile file,String folderName) {
        String url = folderName + "/";
        String uniqueFileName = url + FileUtil.makeFileName(file);
        ObjectMetadata metadata = FileUtil.makeObjectMetadata(file);
        try {
            amazonS3Client.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);
        } catch (IOException exceptionMessage) {
            throw new RestApiException(CommonExceptionCode.IMAGE_FORMAT_ERROR);
        }
        return uniqueFileName;
    }

    @Override
    public String uploadFileToTemp(MultipartFile file) {
        String originalFilename = FileUtil.makeFileName(file);
        String uniqueFileName = tempFolder + originalFilename ;
        ObjectMetadata metadata = FileUtil.makeObjectMetadata(file);

        try {
            amazonS3Client.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);
        } catch (IOException exceptionMessage) {
            throw new RestApiException(CommonExceptionCode.IMAGE_ERROR);
        }
        return originalFilename;
    }

    @Override
    public void moveTempToFinal(String fileName, String targetFolder) {
        String sourceKey = tempFolder + fileName;
        String destinationKey = targetFolder + "/" + fileName;

        if (amazonS3Client.doesObjectExist(bucketName, sourceKey)) {
            amazonS3Client.copyObject(bucketName, sourceKey, bucketName, destinationKey);
            amazonS3Client.deleteObject(bucketName, sourceKey);
        } else {
            throw new RestApiException(CommonExceptionCode.IMAGE_NOT_TEMP);
        }
    }

    @Override
    public void deleteImageFile(String FileName) {

    }

    @Override
    public String uploadProfileImage(MultipartFile file, int targetWidth, int targetHeight) {
        return "";
    }
}
