package org.toastit_v2.feature.aws.service;


import com.amazonaws.services.s3.model.ObjectMetadata;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class S3UploadServiceImplTest {

    private S3UploadServiceImpl s3UploadService;



    MultipartFile file = new MockMultipartFile("testFile", "test.txt", "text/plain", "12345".getBytes());


    @Test
    @DisplayName("파일이름 UUID로 만들기")
    void makeFileName(){
        String fileName = s3UploadService.makeFileName(file);
        assertNotNull(fileName);
    }

    @Test
    @DisplayName("메타데이터 확인하기")
    void confirmMetaData(){
        ObjectMetadata objectMetadata = s3UploadService.makeObjectMetadata(file);
        String contentType = objectMetadata.getContentType();
        long contentLength = objectMetadata.getContentLength();

        Assertions.assertThat(contentType).isEqualTo("text/plain");
        Assertions.assertThat(contentLength).isEqualTo(5);
        assertNotNull(objectMetadata);

    }
    @Test
    @DisplayName("파일 업로드 테스트")
    void upLoadTest() throws IOException {


    }

}