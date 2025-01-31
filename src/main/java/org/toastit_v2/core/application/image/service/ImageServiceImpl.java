
package org.toastit_v2.core.application.image.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import org.toastit_v2.common.exception.custom.CustomAWSException;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.application.image.util.FileUtil;
import org.toastit_v2.core.application.image.util.resizer.ImageResizer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.toastit_v2.core.application.image.util.FileUtil.makeFileName;

@Validated
@Service
@Qualifier("prod")
public class ImageServiceImpl implements ImageService {

    private final AmazonS3Client amazonS3Client;
    private final String bucketName;
    private final static String tempFolder = "temporary/";
    private final static String profileFolder = "profileImage/";

    public ImageServiceImpl(
            AmazonS3Client amazonS3Client,
            @NotNull @Value("${cloud.aws.s3.bucket}") String bucketName
            ) {
        this.amazonS3Client = amazonS3Client;
        this.bucketName = bucketName;

    }

/**
     * AWS bucket 에 파일과 폴더명을 입력하여 파일을 해당 폴더에 업로드 하는 메서드 입니다.
     *
     * @param file 업로드 하고자 하는 파일입니다
     * @param folderName 파일을 올리려는 폴더명 입니다.
     * @return UUID 가 포함된 파일명을 반환합니다.
     */
    @Override
    public String uploadFile(MultipartFile file,String folderName) {
        String url= folderName + "/";
        String uniqueFileName = url + makeFileName(file);
        ObjectMetadata metadata = FileUtil.makeObjectMetadata(file);
        try {
            amazonS3Client.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);
        } catch (IOException exceptionMessage) {
            throw new CustomAWSException(ExceptionCode.IMAGE_FORMAT_ERROR);
        }
        return uniqueFileName;
    }

/**
     * 파일을 임시로 업로드 할 때 사용하는 메서드 입니다.
     *
     * @param file 임시로 업로드 하고자 하는 파일입니다
     * @return 임시로 업로드가 완료된 파일의 UUID 가 포함된 파일명을 반환합니다.
     */
    @Override
    public String uploadFileToTemp(MultipartFile file) {
        String originFileNane = FileUtil.makeFileName(file);
        String uniqueFileName = tempFolder + originFileNane;
        ObjectMetadata metadata = FileUtil.makeObjectMetadata(file);
        try {
            amazonS3Client.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);
        } catch (IOException exceptionMessage) {
            throw new CustomAWSException(ExceptionCode.IMAGE_ERROR);
        }
        return originFileNane;
    }

/**
     * 임시 폴더에 저장된 파일을 원하고자 하는 폴더에 이동시키는 메서드 입니다.
     *
     * @param fileName 이동 시키고자 하는 파일명 입니다.
     * @param targetFolder 이동 시키고자 하는 폴더명 입니다.
     */

    @Override
    public void moveTempToFinal(String fileName, String targetFolder) {
        String sourceKey = tempFolder + fileName;
        String destinationKey = targetFolder + "/" + fileName;
        if (amazonS3Client.doesObjectExist(bucketName, sourceKey)) {
            amazonS3Client.copyObject(bucketName, sourceKey, bucketName, destinationKey);
            amazonS3Client.deleteObject(bucketName, sourceKey);
        } else {
            throw new CustomAWSException(ExceptionCode.IMAGE_NOT_TEMP);
        }
    }

/**
     * 파일 이름을 입력하여 S3 Bucket 에서 삭제하는 메서드 입니다. (UUID 가 포함된 파일 이름이여야 합니다)
     *
     * @param FileName : 삭제하고자 하는 파일 이름입니다.
     */

    @Override
    public void deleteImageFile(String FileName) {
        if (amazonS3Client.doesObjectExist(bucketName, FileName)) {
            amazonS3Client.deleteObject(bucketName, FileName);
        } else {
            throw new CustomAWSException(ExceptionCode.IMAGE_NOT_FOUND);
        }
    }

/**
     * 프로필 이미지를 업로드 하는 메서드 입니다. Width 와 Height 를 입력하여 특정 사이즈로 리사이징 하여 업로드 합니다.
     *
     * @param file 업로드 하고자 하는 프로필 이미지 입니다.
     * @param width 이미지의 Width 크기를 설정합니다.
     * @param height 사진의 Height 크기를 설정합니다.
     * @return 업로드 된 프로필 이미지 이름(UUID 포함)을 반환합니다.
     */
    @Override
    public String uploadProfileImage(MultipartFile file,int width, int height) {
        String uniqueFileName = profileFolder + makeFileName(file);
        ObjectMetadata metadata = FileUtil.makeObjectMetadata(file);
        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            BufferedImage resizedImage = ImageResizer.resizeImage(originalImage, width, height);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", byteArrayOutputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            amazonS3Client.putObject(
                    bucketName,
                    uniqueFileName,
                    inputStream,
                    metadata
            );
        } catch (IOException exceptionMessage) {
            throw new CustomAWSException(ExceptionCode.IMAGE_FORMAT_ERROR);
        }
        return uniqueFileName;
    }
}
