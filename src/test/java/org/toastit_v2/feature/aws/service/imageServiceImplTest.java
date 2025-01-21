package org.toastit_v2.feature.aws.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.toastit_v2.feature.aws.application.service.ImageService;
import org.toastit_v2.feature.aws.application.util.FileUtil;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class imageServiceImplTest {

    private ImageService imageService;

    public imageServiceImplTest(@Qualifier("test")ImageService imageService) {
        this.imageService = imageService;
    }

    MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "123456789".getBytes());

    /**
     * 파일을 첨부하면 UUID 가 추가되어 반환되는지 확인하는 메서드 입니다.
     */
    @Test
    void UUID_생성_테스트(){
        String fileName = FileUtil.makeFileName(file);
        assertThat(fileName).isNotEqualTo(file.getOriginalFilename());
    }

    /**
     * 첨부된 파일의 메타데이터를 추출하여 만들어진 객체의 내용을 확인하는 메서드 입니다.
     */
    @Test
    void 메타데이터_생성_확인_테스트() {
        ObjectMetadata objectMetadata = FileUtil.makeObjectMetadata(file);
        String contentType = objectMetadata.getContentType();
        long contentLength = objectMetadata.getContentLength();
        assertThat(contentType).isEqualTo("text/plain");
        assertThat(contentLength).isEqualTo(file.getSize());
    }

    /**
     * 테스트 파일을 업로드하고, 로컬 스택 저장소에 실제로 되어있는지 확인하는 테스트 코드입니다.
     * 저장소에 파일이 있으면 테스트가 성공합니다. (not null)
     * 업로드 한 파일명과, 업로드 된 파일명의 이름이 다르면 테스트가 성공합니다.(UUID 가 추가되어 업로드 됩니다.)
     * 지정한 폴더에 해당 파일이 존재할경우 테스트가 성공합니다.
     *
     * @throws IOException 파일 형식이 잘못되면 에러 메시지를 반환합니다.
     */
    @Test
    void 파일_업로드_테스트() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello World".getBytes());
        String folderName = "myUploadTestFolder";
        String fileUrl = imageService.uploadFile(file,folderName);
        assertThat(fileUrl).isNotNull();
        assertThat(fileUrl).isNotEqualTo(file.getOriginalFilename());
        assertThat(folderName+"/"+fileUrl).isNotNull();
    }

    /**
     * 테스트 파일을 임시 저장 폴더에 업로드하고, 임시 저장 폴더에 실제로 파일이 있는지 검증하는 테스트 코드 입니다.
     * temporary 폴더 내에 파일이 업로드 되면 테스트가 성공합니다.
     *
     * @throws IOException 업로드 실패 시 에러 메시지를 반환합니다.
     */
    @Test
    void 임시저장_업로드_테스트() throws IOException {
        MultipartFile tempFile = new MockMultipartFile("tempFile", "test.txt", "text/plain", "Hello World".getBytes());
        String tempFileUrl = imageService.uploadFileToTemp(tempFile);
        assertThat(tempFileUrl).isNotNull();
    }

    /**
     * 폴더 이동을 하고자 하는 파일을 원하는 폴더에 이동시키는 메서드 입니다.
     * 특정 파일을 임시 폴더에 저장한 후 원하는 폴더명과 임시 저장된 파일명을 입력하여 이동시킵니다.
     * 사용자가 입력한 폴더명에 파일이 존재하면 테스트가 성공합니다.
     *
     * @throws IOException 임시저장 폴더에 파일이 없다면 에러 메시지를 반환합니다
     */
    @Test
    void 특정폴더로_파일을_옮기는_테스트() throws IOException {
        String fileName = "editTestFile.jpg";
        MultipartFile movingTestFile = new MockMultipartFile("editTestFile", fileName, "image/jpeg", "test image content".getBytes());
        String uploadedFileToTemp = imageService.uploadFileToTemp(movingTestFile);
        String anyFolderName = "myFolder";
        imageService.moveTempToFinal(uploadedFileToTemp,anyFolderName);
        assertThat(uploadedFileToTemp).isNotNull();
        assertThat(anyFolderName+"/"+uploadedFileToTemp).isNotNull();
    }
}
