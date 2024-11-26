package org.toastit_v2.feature.aws.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 * S3에 파일을 업로드하는 서비스.
 */
public interface S3UpLoadService {
    /**
     * 파일을 S3에 업로드합니다.
     *
     * @param file 업로드할 파일
     * @return 업로드된 파일의 URL
     * @throws IOException 파일 업로드 중 오류 발생 시
     */
    String uploadFile(MultipartFile file) throws IOException;

    /**
     * 임시 폴더에 파일을 업로드합니다.
     *
     * @param file 업로드할 파일
     * @return 임시 폴더에 업로드된 파일의 URL
     * @throws IOException 파일 업로드 중 오류 발생 시
     */
    String uploadFileToTemp(MultipartFile file) throws IOException;

    /**
     * 임시 폴더에 있는 파일을 최종 폴더로 이동합니다.
     *
     * @param tempFileName 이동할 임시 파일의 이름
     */
    String moveFileToFinal(String tempFileName);
}
