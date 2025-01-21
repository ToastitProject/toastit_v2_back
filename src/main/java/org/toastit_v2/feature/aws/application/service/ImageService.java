package org.toastit_v2.feature.aws.application.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface imageService {

    String uploadFile(MultipartFile file,String folderName) throws IOException;

    String uploadFileToTemp(MultipartFile file) throws IOException;

    void moveFileToFinal(String tempFileName,String folderName) throws IOException;
}
