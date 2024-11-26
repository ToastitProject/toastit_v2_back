package org.toastit_v2.feature.aws.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;



@SpringBootTest
class S3UpLoadServiceImplWithLocalStackTest {

    @Autowired
    private S3UpLoadServiceImplWithLocalStack s3UpLoadService;

    private AmazonS3 s3Client;

    @BeforeEach
    public void setUp() {
        s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-1"))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("test", "test")))
                .enablePathStyleAccess()
                .build();

    }

    @Test
    @DisplayName("버킷에 파일 업로드")
    void uploadFile() throws IOException {
        // Given
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello World".getBytes());

        // When
        String fileUrl = s3UpLoadService.uploadFile(file);

        // Then
        Assertions.assertThat(fileUrl).isNotNull();
        Assertions.assertThat(fileUrl).isNotEqualTo(file.getOriginalFilename());

    }

    @Test
    @DisplayName("파일 임시 저장")
    void uploadFileToTemp() throws IOException {
        // Given
        MultipartFile tempFile = new MockMultipartFile("tempFile", "test.txt", "text/plain", "Hello World".getBytes());

        // When
        String tempFileUrl = s3UpLoadService.uploadFileToTemp(tempFile);

        //Then
        Assertions.assertThat(tempFileUrl).isNotNull();
    }

    @Test
    @DisplayName("임시저장 파일 -> 특정 폴더로 저장")
    void moveFileToTarget() throws IOException {
        // Given
        String fileName = "editTestFile.jpg";
        MultipartFile movingTestFile = new MockMultipartFile("editTestFile", fileName, "image/jpeg", "test image content".getBytes());

        // When 1.파일을 임시 폴더에 저장
        String tempFileUrl = s3UpLoadService.uploadFileToTemp(movingTestFile);

        // When 2.임시폴더에 저장된 파일을 확인 후, final 폴더로 복사 후 삭제
        String finalFileName = s3UpLoadService.moveFileToFinal(tempFileUrl);

        //Then
        Assertions.assertThat(tempFileUrl).isNotEqualTo(finalFileName);

    }

}