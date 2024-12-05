package org.toastit_v2.feature.aws.application.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
public class S3UpLoadServiceImpl implements S3UpLoadService, FileNameService {

    private final AmazonS3Client amazonS3Client;

    private final String bucketName;

    private final String tempFolder = "temporary/";

    private final String targetFolder = "final/";

    public S3UpLoadServiceImpl(
            AmazonS3Client amazonS3Client,
            @Value("${AWS_BUCKET_NAME}") String bucketName) {
        this.amazonS3Client = amazonS3Client;
        this.bucketName = bucketName;
    }

   @Override
    public String makeFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + originalFilename;
    }

    @Override
    public ObjectMetadata makeObjectMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        return metadata;
    }

    @Override
    public String parseFileNameFromUrl(String url) {
        String basePath = "http://localhost:4566/testbucket/temporary/";
        if (url.startsWith(basePath)) {
            return url.substring(basePath.length());
        } else {
            throw new IllegalArgumentException("주어진 URL이 올바른 형식이 아닙니다: " + url);
        }
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String uniqueFileName = makeFileName(file);
        ObjectMetadata metadata = makeObjectMetadata(file);
        try {
            amazonS3Client.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);
        } catch (IOException exceptionMessage) {
            throw new RuntimeException("파일 업로드 과정에서 문제가 발생했습니다.", exceptionMessage);
        }
        return amazonS3Client.getUrl(bucketName, uniqueFileName).toString();
    }

    @Override
    public String uploadFileToTemp(MultipartFile file) throws IOException {
        String uniqueFileName = tempFolder + makeFileName(file);
        ObjectMetadata metadata = makeObjectMetadata(file);
        try {
            amazonS3Client.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);
        } catch (IOException exceptionMessage) {
            throw new RuntimeException("임시 폴더에 파일 업로드 과정에서 문제가 발생했습니다.", exceptionMessage);
        }
        return amazonS3Client.getUrl(bucketName, uniqueFileName).toString();
    }

    @Override
    public String moveFileToFinal(String tempFileUrl) {
        String FileNameWithUUIDInTempFolder = parseFileNameFromUrl(tempFileUrl);
        String sourceKey = tempFolder + FileNameWithUUIDInTempFolder;
        String destinationKey = targetFolder + FileNameWithUUIDInTempFolder;
        if (amazonS3Client.doesObjectExist(bucketName, sourceKey)) {
            amazonS3Client.copyObject(bucketName, sourceKey, bucketName, destinationKey);
            amazonS3Client.deleteObject(bucketName, sourceKey);
        } else {
            throw new RuntimeException("해당 파일이 임시 폴더에 존재하지 않습니다: " + sourceKey);
        }
        return amazonS3Client.getUrl(bucketName, destinationKey).toString();
    }
}