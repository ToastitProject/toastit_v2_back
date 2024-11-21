package org.toastit_v2.feature.aws.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.toastit_v2.feature.aws.service.S3UploadService;

import java.io.IOException;

@RestController
@RequestMapping("/test/upload")
public class AWSS3UploadController {

    private final S3UploadService s3UploadService;

    public AWSS3UploadController(S3UploadService s3UploadService) {
        this.s3UploadService = s3UploadService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            s3UploadService.upLoadFile(file);
            return "파일을 업로드 하였습니다. file name : "+file.getOriginalFilename();
        } catch (IOException e) {
            return "파일 업로드에 실패했습니다. " + e.getMessage();
        }
    }

}

