/*
package org.toastit_v2.core.application.image.util.fileUploader;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.toastit_v2.common.common.application.code.CommonExceptionCode;
import org.toastit_v2.common.common.application.exception.RestApiException;
import org.toastit_v2.core.application.image.awsS3ClientProvider.AWSS3ClientProvider;
import org.toastit_v2.core.application.image.util.FileUtil;
import org.toastit_v2.core.application.image.util.resizer.ImageResizer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import static org.toastit_v2.core.application.image.util.FileUtil.makeFileName;

@Component
public class ImageUploader {

    private final AWSS3ClientProvider awsS3ClientProvider;
    private final String tempFolder = "temporary/";
    private final String profileImage = "profile-image";

    public ImageUploader(AWSS3ClientProvider awsS3ClientProvider) {
        this.awsS3ClientProvider = awsS3ClientProvider;
    }

    public String uploadFile(MultipartFile file, String folderName)  {
        String url= folderName + "/";
        String uniqueFileName = url + makeFileName(file);
        ObjectMetadata metadata = FileUtil.makeObjectMetadata(file);
        try {
            awsS3ClientProvider.getAmazonS3Client().putObject(awsS3ClientProvider.getBucketName(), uniqueFileName, file.getInputStream(), metadata);
        } catch (IOException exceptionMessage) {
            throw new RestApiException(CommonExceptionCode.IMAGE_FORMAT_ERROR);
        }
        return uniqueFileName;
    }

    public String uploadFileToTemp(MultipartFile file) {
        String originFileNane = FileUtil.makeFileName(file);
        String uniqueFileName = tempFolder + originFileNane;
        ObjectMetadata metadata = FileUtil.makeObjectMetadata(file);
        try {
            awsS3ClientProvider.getAmazonS3Client().putObject(awsS3ClientProvider.getBucketName(), uniqueFileName, file.getInputStream(), metadata);
        } catch (IOException exceptionMessage) {
            throw new RestApiException(CommonExceptionCode.IMAGE_ERROR);
        }
        return originFileNane;
    }

    public String uploadProfileImage(MultipartFile file, int targetWidth, int targetHeight) {
        String url = profileImage + "/";
        String uniqueFileName = url + makeFileName(file);
        ObjectMetadata metadata = FileUtil.makeObjectMetadata(file);

        try {
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            BufferedImage resizedImage = ImageResizer.resizeImage(originalImage, targetWidth, targetHeight);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", byteArrayOutputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            awsS3ClientProvider.getAmazonS3Client().putObject(
                    awsS3ClientProvider.getBucketName(),
                    uniqueFileName,
                    inputStream,
                    metadata
            );
        } catch (IOException exceptionMessage) {
            throw new RestApiException(CommonExceptionCode.IMAGE_FORMAT_ERROR);
        }
        return uniqueFileName;
    }
}
*/
