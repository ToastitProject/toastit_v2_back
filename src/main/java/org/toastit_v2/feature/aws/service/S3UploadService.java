package org.toastit_v2.feature.aws.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface S3UploadService {

    String upLoadFile(MultipartFile file) throws IOException;

    String uploadFileToTemp(MultipartFile file) throws IOException;

    void moveTempFolderToGeneralFolder(String tempFileName);
}
