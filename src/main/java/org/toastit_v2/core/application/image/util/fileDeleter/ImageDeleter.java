/*
package org.toastit_v2.core.application.image.util.fileDeleter;

import org.springframework.stereotype.Component;
import org.toastit_v2.common.common.application.code.CommonExceptionCode;
import org.toastit_v2.common.common.application.exception.RestApiException;
import org.toastit_v2.core.application.image.awsS3ClientProvider.AWSS3ClientProvider;

@Component
public class ImageDeleter {

    private final AWSS3ClientProvider awsS3ClientProvider;

    public ImageDeleter(AWSS3ClientProvider awsS3ClientProvider) {
        this.awsS3ClientProvider = awsS3ClientProvider;
    }

    public void deleteImage(String fileName) {
        String objectKey = fileName;
        if (awsS3ClientProvider.getAmazonS3Client().doesObjectExist(awsS3ClientProvider.getBucketName(), objectKey)) {
            awsS3ClientProvider.getAmazonS3Client().deleteObject(awsS3ClientProvider.getBucketName(), objectKey);
        } else {
            throw new RestApiException(CommonExceptionCode.IMAGE_NOT_FOUND);
        }
    }
}
*/
