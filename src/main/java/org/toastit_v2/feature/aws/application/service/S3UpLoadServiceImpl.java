package org.toastit_v2.feature.aws.application.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.exception.RestApiException;
import org.toastit_v2.feature.aws.application.util.FileUtil;
import java.io.IOException;

import static org.toastit_v2.feature.aws.application.util.FileUtil.makeFileName;

@Validated
@Service
public class S3UpLoadServiceImpl implements S3UpLoadService {

    private final AmazonS3Client amazonS3Client;
    private final String bucketName;

    private final String tempFolder = "temporary/";

    public S3UpLoadServiceImpl(
            AmazonS3Client amazonS3Client,
            @NotNull @Value("${AWS_BUCKET_NAME}") String bucketName) {
        this.amazonS3Client = amazonS3Client;
        this.bucketName = bucketName;
    }

    /**
     * AWS bucket 에 파일과 폴더명을 입력하여 파일을 해당 폴더에 업로드 하는 메서드 입니다.
     *
     * @param file 업로드 하고자 하는 파일입니다
     * @param folderName 파일을 올리려는 폴더명 입니다.
     * @return UUID 가 포함된 파일명을 반환합니다.
     * @throws IOException 파일의 형식이 다를경우 에러 메시지를 반환합니다.
     */
    @Override
    public String uploadFile(MultipartFile file,String folderName) throws IOException {
        String url= folderName + "/";
        String uniqueFileName = url + makeFileName(file);
        ObjectMetadata metadata = FileUtil.makeObjectMetadata(file);
        try {
            amazonS3Client.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);
        } catch (IOException exceptionMessage) {
            throw new RestApiException(CommonExceptionCode.IMAGE_FORMAT_ERROR);
        }
        return uniqueFileName;
    }

    /**
     * 파일을 임시로 업로드 할 때 사용하는 메서드 입니다.
     *
     * @param file 임시로 업로드 하고자 하는 파일입니다
     * @return 임시로 업로드가 완료된 파일의 UUID 가 포함된 파일명을 반환합니다.
     * @throws IOException 업로드 실패시 에러 메시지를 반환 합니다.
     */
    @Override
    public String uploadFileToTemp(MultipartFile file) throws IOException {
        String originFileNane = FileUtil.makeFileName(file);
        String uniqueFileName = tempFolder + originFileNane;
        ObjectMetadata metadata = FileUtil.makeObjectMetadata(file);
        try {
            amazonS3Client.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);
        } catch (IOException exceptionMessage) {
            throw new RestApiException(CommonExceptionCode.IMAGE_ERROR);
        }
        return originFileNane;
    }

    /**
     * 임시 폴더에 저장된 파일을 원하고자 하는 폴더에 이동시키는 메서드 입니다.
     *
     * @param fileName 이동 시키고자 하는 파일명 입니다.
     * @param targetFolder 이동 시키고자 하는 폴더명 입니다.
     * @throws IOException 임시 폴더에 파일이 있지 않는 경우 에러 메시지를 반환합니다.
     */
    @Override
    public void moveFileToFinal(String fileName, String targetFolder) throws IOException {
        String sourceKey = tempFolder + fileName;
        String destinationKey = targetFolder + "/" + fileName;
        if (amazonS3Client.doesObjectExist(bucketName, sourceKey)) {
            amazonS3Client.copyObject(bucketName, sourceKey, bucketName, destinationKey);
            amazonS3Client.deleteObject(bucketName, sourceKey);
        } else {
            throw new RestApiException(CommonExceptionCode.IMAGE_NOT_TEMP);
        }
    }
}
