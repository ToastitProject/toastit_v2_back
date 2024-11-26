package org.toastit_v2.feature.aws.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.toastit_v2.feature.aws.application.service.S3UpLoadService;
import org.toastit_v2.feature.aws.application.service.S3UpLoadServiceImpl;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class S3UpLoadServiceImplTest {

    private S3UpLoadServiceImpl s3UpLoadService;

    MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "123456789".getBytes());


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

}