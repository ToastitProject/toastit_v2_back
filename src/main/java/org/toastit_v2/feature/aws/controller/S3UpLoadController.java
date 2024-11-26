package org.toastit_v2.feature.aws.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.toastit_v2.feature.aws.service.S3UpLoadService;

import java.io.IOException;

@RestController
@RequestMapping("/test/upload")
public class S3UpLoadController {

    private final S3UpLoadService s3UpLoadService;

    public S3UpLoadController(S3UpLoadService s3UpLoadService) {
        this.s3UpLoadService = s3UpLoadService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            s3UpLoadService.uploadFile(file);
            return "파일을 업로드 하였습니다. 파일 이름 : "+file.getOriginalFilename();
        } catch (IOException e) {
            return "파일 업로드에 실패했습니다. " + e.getMessage();
        }
    }
}

