package org.toastit_v2.feature.aws.application.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

public interface FileNameService {
    /**
     *
     * 업로드 할 파일의 이름에 UUID 를 추가합니다
     *
     * @param file 업로드할 파일 입니다
     * @return UUID 가 추가된 파일 명
     */
    String makeFileName(MultipartFile file);

    /**
     *
     * 업로드 할 파일의 메타데이터를 생성합니다.
     *
     * @param file 업로드 할 파일입니다.
     * @return 파일의 size, type 을 담아 ObjectMetadata 타입으로 반환합니다.
     */
    ObjectMetadata makeObjectMetadata(MultipartFile file);

    /**
     * 주어진 URL에서 파일명을 추출합니다.
     *
     * @param url 파일의 전체 URL
     * String basePath : 제거할 URL
     * @return 추출된 파일명
     */
    String parseFileNameFromUrl(String url);
}
