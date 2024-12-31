//package org.toastit_v2.feature.aws.service;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import jakarta.validation.constraints.NotNull;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Service;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.multipart.MultipartFile;
//import org.toastit_v2.feature.aws.application.util.FileNameService;
//import org.toastit_v2.feature.aws.application.service.S3UpLoadService;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Primary
//@Service
//@Validated
//@ActiveProfiles("test")
//public class S3UpLoadServiceLocalStack implements S3UpLoadService , FileNameService {
//
//    private final AmazonS3Client amazonS3Client;
//
//    private final String bucketName;
//
//    private final String tempFolder = "temporary/";
//
//
//    public S3UpLoadServiceLocalStack(
//            AmazonS3Client amazonS3Client,
//            @NotNull @Value("${cloud.aws.s3.bucket}") String bucketName
//    ) {
//        this.amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566","us-east-1"))
//                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("test","test")))
//                .enablePathStyleAccess()
//                .build();
//        this.bucketName = bucketName;
//        if (!amazonS3Client.doesBucketExistV2(bucketName)) {
//            amazonS3Client.createBucket(bucketName);
//        }
//    }
//
//    @Override
//    public String makeFileName(MultipartFile file) {
//        String originalFilename = file.getOriginalFilename();
//        String uuid = UUID.randomUUID().toString();
//        return uuid + "_" + originalFilename;
//    }
//
//    @Override
//    public ObjectMetadata makeObjectMetadata(MultipartFile file) {
//        ObjectMetadata metadata = new ObjectMetadata();
//        metadata.setContentLength(file.getSize());
//        metadata.setContentType(file.getContentType());
//        return metadata;
//    }
//
//    @Override
//    public String uploadFile(MultipartFile file,String folderName) throws IOException {
//        String url = folderName + "/";
//        String uniqueFileName = url + makeFileName(file);
//        ObjectMetadata metadata = makeObjectMetadata(file);
//        try {
//            amazonS3Client.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);
//        } catch (IOException exceptionMessage) {
//            throw new RuntimeException("파일 업로드 과정에서 문제가 발생했습니다.", exceptionMessage);
//        }
//        return uniqueFileName;
//    }
//
//    @Override
//    public String uploadFileToTemp(MultipartFile file) throws IOException {
//        String originalFilename = makeFileName(file);
//        String uniqueFileName = tempFolder + originalFilename ;
//        ObjectMetadata metadata = makeObjectMetadata(file);
//        try {
//            amazonS3Client.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);
//        } catch (IOException exceptionMessage) {
//            throw new RuntimeException("임시 폴더에 파일 업로드 과정에서 문제가 발생했습니다.", exceptionMessage);
//        }
//        return originalFilename;
//    }
//
//    @Override
//    public void moveFileToFinal(String fileName,String targetFolder) {
//        String sourceKey = tempFolder + fileName;
//        String destinationKey = targetFolder + "/" + fileName;
//        if (amazonS3Client.doesObjectExist(bucketName, sourceKey)) {
//            amazonS3Client.copyObject(bucketName, sourceKey, bucketName, destinationKey);
//            amazonS3Client.deleteObject(bucketName, sourceKey);
//        } else {
//            throw new RuntimeException("해당 파일이 임시 폴더에 존재하지 않습니다: " + sourceKey);
//        }
//
//    }
//
//}
