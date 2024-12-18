package org.toastit_v2.feature.aws.application.util;

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

}
