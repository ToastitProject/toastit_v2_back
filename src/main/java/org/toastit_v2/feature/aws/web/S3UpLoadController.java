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
@RequestMapping("/test")
public class S3UpLoadController {

    private final S3UpLoadService s3UpLoadService;

    public S3UpLoadController(S3UpLoadService s3UpLoadService, UserService userService) {
        this.s3UpLoadService = s3UpLoadService;
    }

    @PostMapping(value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            String UploadedFileName = s3UpLoadService.uploadFile(file);
            return "파일을 업로드 하였습니다. 파일 이름 : "+file.getOriginalFilename() +
                    " Bucket 에 업로드 된 이릅 : "+ UploadedFileName;
        } catch (IOException e) {
            return "파일 업로드에 실패했습니다. " + e.getMessage();
        }
    }

    @PostMapping(value = "/temporaryUpload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String temporaryUpload(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            String uploadedFileToTemp = s3UpLoadService.uploadFileToTemp(file);
            return "파일을 임시 폴더에 업로드 하였습니다. 파일 이름 : "+file.getOriginalFilename()+
                    " temporary 디렉토리에 업로드 된 이름 : "+ uploadedFileToTemp;
        } catch (IOException e) {
            return "파일을 임시저장에 실패했습니다. " + e.getMessage();
        }
    }
}


