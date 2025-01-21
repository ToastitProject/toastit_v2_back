package org.toastit_v2.feature.aws.web;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.toastit_v2.feature.aws.application.service.S3UpLoadService;
import java.io.IOException;

@RestController
@RequestMapping("/uploads")
public class S3UpLoadController {

    private final S3UpLoadService s3UpLoadService;

    public S3UpLoadController(S3UpLoadService s3UpLoadService) {
        this.s3UpLoadService = s3UpLoadService;
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String upload(@RequestParam("file") MultipartFile file,String folderName) throws IOException {
            return s3UpLoadService.uploadFile(file,folderName);
    }

    @PostMapping(value = "/temporary",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String temporary(@RequestParam("file") MultipartFile file) throws IOException {
       return s3UpLoadService.uploadFileToTemp(file);
    }

    @PostMapping("/move")
    public void move(String fileName, String folderName) throws IOException {
        s3UpLoadService.moveFileToFinal(fileName,folderName);
    }
}
