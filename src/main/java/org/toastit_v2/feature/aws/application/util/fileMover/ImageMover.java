package org.toastit_v2.feature.aws.application.util.fileMover;

import org.springframework.stereotype.Component;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.exception.RestApiException;
import org.toastit_v2.feature.aws.application.awsS3ClientProvider.AWSS3ClientProvider;

@Component
public class ImageMover {

    private final AWSS3ClientProvider awsS3ClientProvider;
    private final String tempFolder = "temporary/";

    public ImageMover(AWSS3ClientProvider awsS3ClientProvider) {
        this.awsS3ClientProvider = awsS3ClientProvider;
    }

    public void moveTempToFinal(String fileName, String targetFolder) {
        String sourceKey = tempFolder + fileName;
        String destinationKey = targetFolder + "/" + fileName;
        if (awsS3ClientProvider.getAmazonS3Client().doesObjectExist(awsS3ClientProvider.getBucketName(), sourceKey)) {
            awsS3ClientProvider.getAmazonS3Client().copyObject(awsS3ClientProvider.getBucketName(), sourceKey, awsS3ClientProvider.getBucketName(), destinationKey);
            awsS3ClientProvider.getAmazonS3Client().deleteObject(awsS3ClientProvider.getBucketName(), sourceKey);
        } else {
            throw new RestApiException(CommonExceptionCode.IMAGE_NOT_TEMP);
        }
    }
}
