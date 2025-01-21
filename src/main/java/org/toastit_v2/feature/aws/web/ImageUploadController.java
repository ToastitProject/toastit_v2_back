package org.toastit_v2.feature.aws.web;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.toastit_v2.feature.aws.application.service.ImageService;
import java.io.IOException;

@RestController
public class S3ImageController {

    private final ImageService imageService;

    public S3ImageController(@Qualifier("prod") ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/uploads",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String upload(@RequestParam("file") MultipartFile file,String folderName) throws IOException {
            return imageService.uploadFile(file,folderName);
    }

    @PostMapping(value = "/uploads/temporary",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String temporary(@RequestParam("file") MultipartFile file) throws IOException {
       return imageService.uploadFileToTemp(file);
    }

    @PostMapping("/move")
    public void move(String fileName, String folderName) throws IOException {
        imageService.moveTempToFinal(fileName,folderName);
    }

    @PostMapping("/uploads/profileImage")
    public String uploadProfileImage(@RequestParam("file") MultipartFile file){
        return imageService.uploadProfileImage(file,100,100);
    }

    @DeleteMapping("/deleteImage")
    public void deleteImage(@RequestParam("file") MultipartFile file){
        return imageService.deleteImageFile(file.getOriginalFilename());
    }
}
