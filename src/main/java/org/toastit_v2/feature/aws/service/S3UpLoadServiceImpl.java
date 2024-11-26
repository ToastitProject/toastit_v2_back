package org.toastit_v2.feature.aws.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
public class S3UpLoadServiceImpl implements S3UpLoadService {

    private final AmazonS3Client amazonS3Client;

    private final String bucketName;

    private final String tempFolder = "temporary/";

    private final String targetFolder = "final/";

    public S3UpLoadServiceImpl(
            AmazonS3Client amazonS3Client,
            @Value("${AWS_BUCKET_NAME}") String bucketName) {
        this.amazonS3Client = amazonS3Client;
        this.bucketName = bucketName;
    }

    /**
     *
     * 업로드 할 파일의 이름에 UUID 를 추가합니다
     *
     * @param file 업로드할 파일 입니다
     * @return UUID 가 추가된 파일 명
     */
    static String makeFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + originalFilename;
    }

    /**
     *
     * 업로드 할 파일의 메타데이터를 생성합니다.
     *
     * @param file 업로드 할 파일입니다.
     * @return 파일의 size, type 을 담아 ObjectMetadata 타입으로 반환합니다.
     */
    static ObjectMetadata makeObjectMetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        return metadata;
    }
    /**
     * 주어진 URL에서 파일명을 추출합니다.
     *
     * @param url 파일의 전체 URL
     * String basePath : 제거할 URL
     * @return 추출된 파일명
     */
    static String parseFileNameFromUrl(String url) {
        String basePath = "http://localhost:4566/testbucket/temporary/";
        if (url.startsWith(basePath)) {
            return url.substring(basePath.length());
        } else {
            throw new IllegalArgumentException("주어진 URL이 올바른 형식이 아닙니다: " + url);
        }
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String uniqueFileName = makeFileName(file);
        ObjectMetadata metadata = makeObjectMetadata(file);
        try {
            amazonS3Client.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);
        } catch (IOException exceptionMessage) {
            throw new RuntimeException("파일 업로드 과정에서 문제가 발생했습니다.", exceptionMessage);
        }
        return amazonS3Client.getUrl(bucketName, uniqueFileName).toString();
    }

    @Override
    public String uploadFileToTemp(MultipartFile file) throws IOException {
        String uniqueFileName = tempFolder + makeFileName(file);
        ObjectMetadata metadata = makeObjectMetadata(file);
        try {
            amazonS3Client.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);
        } catch (IOException exceptionMessage) {
            throw new RuntimeException("임시 폴더에 파일 업로드 과정에서 문제가 발생했습니다.", exceptionMessage);
        }
        return amazonS3Client.getUrl(bucketName, uniqueFileName).toString();
    }

    @Override
    public String moveFileToFinal(String tempFileUrl) {
        String FileNameWithUUIDInTempFolder = parseFileNameFromUrl(tempFileUrl);
        String sourceKey = tempFolder + FileNameWithUUIDInTempFolder;
        String destinationKey = targetFolder + FileNameWithUUIDInTempFolder;
        if (amazonS3Client.doesObjectExist(bucketName, sourceKey)) {
            amazonS3Client.copyObject(bucketName, sourceKey, bucketName, destinationKey);
            amazonS3Client.deleteObject(bucketName, sourceKey);
        } else {
            throw new RuntimeException("해당 파일이 임시 폴더에 존재하지 않습니다: " + sourceKey);
        }
        return amazonS3Client.getUrl(bucketName, destinationKey).toString();
    }
}
