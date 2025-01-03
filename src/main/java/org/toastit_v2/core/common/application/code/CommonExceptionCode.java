package org.toastit_v2.core.common.application.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonExceptionCode implements ResponseCode {

    /**
     * File Error
     */
    FILE_ERROR(HttpStatus.BAD_REQUEST, "파일 에러"),

    /**
     * SSH Error
     */
    SSH_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SSH 연결 실패"),
    SSH_PORT_FORWARDING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "포트 포워딩 설정 실패"),

    /**
     *  Image Error
     */
    // 파일 전역 IO Error 처리
    IMAGE_ERROR(HttpStatus.BAD_REQUEST, "이미지 업로드 에러"),

    // 파일 못찾는 경우 ex) null
    IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "이미지를 찾을 수 없습니다"),

    // 파일 형식을 지키지 않은 경우
    IMAGE_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "이미지 형식이 올바르지 않습니다"),

    // 이미지 리사이즈 실패
    IMAGE_RESIZE_ERROR(HttpStatus.BAD_REQUEST, "이미지 업로드 에러"),

    // 임시 이미지 x
    IMAGE_NOT_TEMP(HttpStatus.BAD_REQUEST, "이미지 에러"),

    /**
     * Token Error for JWT
     */
    // 토큰 만료
    JWT_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    // 토큰 오류
    JWT_INVALID_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    // 토큰 오류
    JWT_UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    // 변조된 토큰
    JWT_UNSUPPORTED_ERROR(HttpStatus.UNAUTHORIZED, "변조된 토큰입니다."),
    // 알 수 없는 오류
    JWT_INTERNAL_ERROR(HttpStatus.UNAUTHORIZED, "알 수 없는 오류가 발생했습니다."),
    // 토큰을 찾을 수 없음
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "리프레시 토큰을 찾을 수 없습니다."),

    /**
     * Email Error for User
     */
    // 사용 중인 이메일
    EXIST_EMAIL_ERROR(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일입니다."),
    // 이메일 인증번호 불일치
    NOT_MATCH_AUTH_CODE(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다."),
    // 이메일 비밀번호 불일치
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "이메일과 비밀번호가 일치하지 않습니다."),

    /**
     * User Error
     */
    // 유저를 찾을 수 없음
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "사용자를 찾을 수 없습니다."),
    // 회원 업데이트 오류
    UPDATE_FAIL_USER(HttpStatus.BAD_REQUEST, "회원 정보 업데이트 실패"),
    // 사용 중인 닉네임
    EXIST_NICKNAME_ERROR(HttpStatus.BAD_REQUEST, "이미 사용 중인 닉네임입니다."),

    /**
     *  Cocktail Search Error
     */
    // 칵테일을 찾을 수 없음
    NOT_FOUND_COCKTAIL(HttpStatus.NOT_FOUND, "칵테일을 찾을 수 없습니다."),
    // 검색 조건이 올바르지 않음
    INVALID_COCKTAIL_SEARCH(HttpStatus.BAD_REQUEST, "검색 조건이 올바르지 않습니다."),
    // 칵테일 개수가 올바르지 않음
    INVALID_COCKTAIL_COUNT(HttpStatus.BAD_REQUEST, "유효하지 않은 칵테일 개수입니다."),

    /**
     * Like Error
     */
    NOT_FOUND_LIKE(HttpStatus.BAD_REQUEST, "미정"),

    /**
     * 4** Client Error
     */
    // 400
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 매개 변수가 포함됨"),
    // 400
    FILED_ERROR(HttpStatus.BAD_REQUEST, "요청값이 올바르지 않습니다."),
    // 401
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다"),
    // 403
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다"),
    // 404
    NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다"),
    // 405
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원되지 않는 메서드입니다"),
    // 408
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "요청 시간이 초과되었습니다"),
    // 415
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "지원되지 않는 미디어 타입입니다"),

    /**
     * 5** Server Error
     */
    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다"),
    // 502
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "잘못된 게이트웨이입니다"),
    // 503
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "서비스를 사용할 수 없습니다"),
    // 504
    GATEWAY_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "게이트웨이 시간 초과입니다"),
    // 505
    HTTP_VERSION_NOT_SUPPORTED(HttpStatus.HTTP_VERSION_NOT_SUPPORTED, "HTTP 버전을 지원하지 않습니다"),
    // 507
    INSUFFICIENT_STORAGE(HttpStatus.INSUFFICIENT_STORAGE, "저장 공간이 부족합니다"),
    // 511
    NETWORK_AUTHENTICATION_REQUIRED(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, "네트워크 인증이 필요합니다");

    private final HttpStatus httpStatus;
    private final String data;

    CommonExceptionCode(HttpStatus httpStatus, String data) {
        this.httpStatus = httpStatus;
        this.data = data;
    }

}
