package org.toastit_v2.feature.aws.application.util;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class FileUtil {

    /**
     * 파일명 입력시 UUID 를 추가하여 반환하는 메서드 입니다.
     *
     * @param file 업로드 하고자 하는 파일입니다.
     * @return UUID 가 추가된 파일명을 반환합니다.
     */
    public static String makeFileName(MultipartFile file){
        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + originalFilename;
    }

    /**
     * 업로드 하고자 하는 파일의 메타데이터를 저장하여 객체로 반환하는 메서드 입니다.
     *
     * @param file 업로드 하고자 하는 파일입니다.
     * @return 업로드 하고자 하는 파일의 메타데이터가 추가된 객체를 반환합니다.
     */
    public static ObjectMetadata makeObjectMetadata(MultipartFile file){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        return metadata;
    }
}
