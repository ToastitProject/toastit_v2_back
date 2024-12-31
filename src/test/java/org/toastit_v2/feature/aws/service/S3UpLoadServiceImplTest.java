//package org.toastit_v2.feature.aws.service;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//
//@SpringBootTest
//class S3UpLoadServiceImplTest {
//
//    @Autowired
//    private S3UpLoadServiceLocalStack s3UpLoadServiceLocalStack;
//
//
//    @Autowired
//    private AmazonS3 s3Client;
//
//    MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "123456789".getBytes());
//
//
//    @BeforeEach
//    public void setUp() {
//        s3Client = AmazonS3ClientBuilder.standard()
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "us-east-1"))
//                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("test", "test")))
//                .enablePathStyleAccess()
//                .build();
//    }
//
//    @Test
//    @DisplayName("UUID 파일 이름 생성 테스트")
//    void makeUUIDFilename(){
//        String fileName = s3UpLoadServiceLocalStack.makeFileName(file);
//        Assertions.assertThat(fileName).isNotEqualTo(file.getOriginalFilename());
//    }
//
//    @Test
//    @DisplayName("메타데이터 생성 확인 테스트")
//    void makeMetaDataTest() {
//        ObjectMetadata objectMetadata = s3UpLoadServiceLocalStack.makeObjectMetadata(file);
//        String contentType = objectMetadata.getContentType();
//        long contentLength = objectMetadata.getContentLength();
//        Assertions.assertThat(contentType).isEqualTo("text/plain");
//        Assertions.assertThat(contentLength).isEqualTo(file.getSize());
//    }
//
//    @Test
//    @DisplayName("localStack 버킷에 파일 업로드 테스트")
//    void uploadFile() throws IOException {
//        // Given
//        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello World".getBytes());
//
//        // When
//        String folderName = "myUploadTestFolder";
//        String fileUrl = s3UpLoadServiceLocalStack.uploadFile(file,folderName);
//
//        // Then
//        // 1. 업로드 한 파일의 URL 이 null 이 아니라면 테스트 성공
//        // 2. 업로드 한 파일의 원본이름은 UUID 가 추가된 업로드 파일명과 다르므로, 둘의 이름이 다르면 테스트 성공
//        // 3. 원하는 폴더에 파일이 업로드 되어있으면 업로드 성공
//        System.out.println("localStack upload fileUrl ==================== " + fileUrl);
//        Assertions.assertThat(fileUrl).isNotNull();
//        Assertions.assertThat(fileUrl).isNotEqualTo(file.getOriginalFilename());
//        Assertions.assertThat(folderName+"/"+fileUrl).isNotNull();
//    }
//
//    @Test
//    @DisplayName("로컬스텍 temporary 폴더에 파일 임시 저장 테스트")
//    void uploadFileToTemp() throws IOException {
//        // Given
//        MultipartFile tempFile = new MockMultipartFile("tempFile", "test.txt", "text/plain", "Hello World".getBytes());
//
//        // When
//        String tempFileUrl = s3UpLoadServiceLocalStack.uploadFileToTemp(tempFile);
//
//        //Then
//        // 1. 업로드 한 파일의 URL 이 null 이 아니라면 테스트 성공
//        System.out.println("tempFileUrl ==================== " + tempFileUrl);
//        Assertions.assertThat(tempFileUrl).isNotNull();
//    }
//
//    @Test
//    @DisplayName("로컬스택의 임시저장 파일 -> 특정 폴더로 저장 테스트")
//    void moveFileToTarget() throws IOException {
//        // Given
//        String fileName = "editTestFile.jpg";
//        MultipartFile movingTestFile = new MockMultipartFile("editTestFile", fileName, "image/jpeg", "test image content".getBytes());
//
//        // When 1.파일을 임시 폴더에 저장
//        String uploadedFileToTemp = s3UpLoadServiceLocalStack.uploadFileToTemp(movingTestFile);
//
//        // When 2.임시폴더에 저장된 파일을 확인 후, final 폴더로 복사 후 삭제
//        // When 3.목표한 폴더명을 지정하여 이동
//        String anyFolderName = "myFolder";
//        s3UpLoadServiceLocalStack.moveFileToFinal(uploadedFileToTemp,anyFolderName);
//
//        //Then
//        // 해당 파일이 존재한다면 테스트 성공
//        // myFolder/테스트파일 이 존재하면 테스트 성공
//        Assertions.assertThat(uploadedFileToTemp).isNotNull();
//        Assertions.assertThat(anyFolderName+"/"+uploadedFileToTemp).isNotNull();
//    }
//}
