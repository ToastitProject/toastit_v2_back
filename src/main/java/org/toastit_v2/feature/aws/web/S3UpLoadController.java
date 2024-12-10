package org.toastit_v2.feature.aws.web;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.toastit_v2.feature.aws.application.service.S3UpLoadService;
import org.toastit_v2.feature.user.application.service.UserService;

import java.io.IOException;

@RestController
@RequestMapping("/uploads")
public class S3UpLoadController {

    private final S3UpLoadService s3UpLoadService;

    public S3UpLoadController(S3UpLoadService s3UpLoadService, UserService userService) {
        this.s3UpLoadService = s3UpLoadService;
    }

    /**
     *
     * @param file : 업로드 하고자 하는 파일입니다.
     * @param folderName : 업로드 하고 싶은 폴더명 입니다.
     * @return : UUID 가 포함된 파일 이름이 리턴됩니다.
     * @throws IOException
     */
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String upload(@RequestParam("file") MultipartFile file,String folderName) throws IOException {
        try {
            String UploadedFileName = s3UpLoadService.uploadFile(file,folderName);
            return UploadedFileName;
        } catch (IOException e) {
            return "파일 업로드에 실패했습니다. " + e.getMessage();
        }
    }

    /**
     *
     * @param : 임시로 저장하고 싶은 파일 이름입니다.
     * @return : UUID 가 포함된 파일 이름이 리턴됩니다.
     * @throws IOException
     */
    @PostMapping(value = "/temporary",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String temporary(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            String uploadedFileToTemp = s3UpLoadService.uploadFileToTemp(file);
            return uploadedFileToTemp;
        } catch (IOException e) {
            return "파일을 임시저장에 실패했습니다. " + e.getMessage();
        }
    }

    /**
     *
     * @param fileName : 옮기고자 하는 파일의 이름입니다 (UUID 를 포함하여야 합니다.)
     * @param folderName : 이동하고자 하는 파일 이름입니다
     * @throws IOException
     */
    @PostMapping(value = "/move")
    public void move(String fileName, String folderName) throws IOException {
        s3UpLoadService.moveFileToFinal(fileName,folderName);
    }
}
