package org.toastit_v2.feature.aws.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.toastit_v2.feature.aws.application.service.S3UpLoadService;
import org.toastit_v2.feature.aws.application.service.S3UpLoadServiceImpl;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class S3UpLoadServiceImplTest {

    @Autowired
    private S3UpLoadServiceImpl s3UpLoadService;

    @Autowired
    private S3UpLoadServiceLocalStack s3UpLoadServiceLocalStack;

    @Autowired
    private AmazonS3 s3Client;

    MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "123456789".getBytes());

    @BeforeEach
    public void setUp() {
        s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-1"))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("test", "test")))
                .enablePathStyleAccess()
                .build();

    }

    @Test
    @DisplayName("UUID 파일 이름 생성 테스트")
    void makeUUIDFilename(){
        String fileName = s3UpLoadService.makeFileName(file);
        Assertions.assertThat(fileName).isNotEqualTo(file.getOriginalFilename());
    }

    @Test
    @DisplayName("메타데이터 생성 확인 테스트")
    void makeMetaDataTest() {
        ObjectMetadata objectMetadata = s3UpLoadService.makeObjectMetadata(file);
        String contentType = objectMetadata.getContentType();
        long contentLength = objectMetadata.getContentLength();
        Assertions.assertThat(contentType).isEqualTo("text/plain");
        Assertions.assertThat(contentLength).isEqualTo(file.getSize());
    }

    @Test
    @DisplayName("(mock)파일 업로드 테스트1")
    void testUploadFile() throws IOException {
        S3UpLoadService service = mock(S3UpLoadService.class);
        MultipartFile mockFile = mock(MultipartFile.class);

        when(mockFile.getOriginalFilename()).thenReturn("test-file.txt");
        when(service.uploadFile(mockFile)).thenReturn("url-to-file");

        String result = service.uploadFile(mockFile);
        assertEquals("url-to-file", result);

    }
    @Test
    @DisplayName("localStack 버킷에 파일 업로드 테스트")
    void uploadFile() throws IOException {
        // Given
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello World".getBytes());

        // When
        String fileUrl = s3UpLoadServiceLocalStack.uploadFile(file);

        // Then
        // 1. 업로드 한 파일의 URL 이 null 이 아니라면 테스트 성공
        // 2. 업로드 한 파일의 원본이름은 UUID 가 추가된 업도르 파일명과 다르므로, 둘의 이름이 다르면 테스트 성공
        System.out.println("localStack upload fileUrl ==================== " + fileUrl);
        Assertions.assertThat(fileUrl).isNotNull();
        Assertions.assertThat(fileUrl).isNotEqualTo(file.getOriginalFilename());

    }

    @Test
    @DisplayName("로컬스텍 temporary 폴더에 파일 임시 저장 테스트")
    void uploadFileToTemp() throws IOException {
        // Given
        MultipartFile tempFile = new MockMultipartFile("tempFile", "test.txt", "text/plain", "Hello World".getBytes());

        // When
        String tempFileUrl = s3UpLoadServiceLocalStack.uploadFileToTemp(tempFile);

        //Then
        // 1. 업로드 한 파일의 URL 이 null 이 아니라면 테스트 성공
        System.out.println("tempFileUrl ==================== " + tempFileUrl);
        Assertions.assertThat(tempFileUrl).isNotNull();
    }

    @Test
    @DisplayName("로컬스택의 임시저장 파일 -> 특정 폴더로 저장 테스트")
    void moveFileToTarget() throws IOException {
        // Given
        String fileName = "editTestFile.jpg";
        MultipartFile movingTestFile = new MockMultipartFile("editTestFile", fileName, "image/jpeg", "test image content".getBytes());

        // When 1.파일을 임시 폴더에 저장
        String tempFileUrl = s3UpLoadServiceLocalStack.uploadFileToTemp(movingTestFile);

        // When 2.임시폴더에 저장된 파일을 확인 후, final 폴더로 복사 후 삭제
        String finalFileName = s3UpLoadServiceLocalStack.moveFileToFinal(tempFileUrl);

        //Then
        // 1. 업로드 한 파일의 URL 이 null 이 아니라면 테스트 성공
        // 2. temporary 경로의 파일 URL 이 final 경로로 이동했으므로 파일 URL 이 다르다면 테스트 성공
        System.out.println("finalFileName ==================== " + finalFileName);
        Assertions.assertThat(finalFileName).isNotNull();
        Assertions.assertThat(tempFileUrl).isNotEqualTo(finalFileName);

    }
}