package org.toastit_v2.core.application.image.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadFile(MultipartFile file,String folderName);

    String uploadFileToTemp(MultipartFile file);

    void moveTempToFinal(String tempFileName, String folderName);

    void deleteImageFile(String FileName);

    String uploadProfileImage(MultipartFile file, int targetWidth, int targetHeight);

}
