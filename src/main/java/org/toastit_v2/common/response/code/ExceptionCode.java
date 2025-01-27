package org.toastit_v2.common.response.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    /**
     *  Image Error Code
     */
    IMAGE_ERROR(HttpStatus.BAD_REQUEST, "이미지 업로드 에러", 400),
    IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "이미지를 찾을 수 없습니다", 400),
    IMAGE_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "이미지 형식이 올바르지 않습니다", 400),
    IMAGE_RESIZE_ERROR(HttpStatus.BAD_REQUEST, "이미지 업로드 에러", 400),
    IMAGE_NOT_TEMP(HttpStatus.BAD_REQUEST, "이미지 에러", 400),

    /**
     * SSH Error
     */
    SSH_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SSH 연결 실패", 500),
    SSH_PORT_FORWARDING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "포트 포워딩 설정 실패", 500),

    /**
     * Base Cocktail Error Code
     */

    /**
     * Craft Cocktail Error Code
     */
    FAIL_CREATE_CRAFT_COCKTAIL(HttpStatus.BAD_REQUEST, "커스텀 칵테일 생성 실패하였습니다.", 400),

    /**
     * Member Error Code
     */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.", 404),
    MEMBER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "사용자 접근이 거부되었습니다.", 403),
    MEMBER_DUPLICATE(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다.", 409),
    MEMBER_INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "잘못된 사용자 인증 정보입니다.", 401),

    PASSWORD_MISS_MATCH_ERROR(HttpStatus.BAD_REQUEST, "패스워드 형식이 일치하지 않습니다.", 400),

    // 존재하지 않는 이메일
    NOT_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "존재하지 않는 이메일입니다.", 400),

    NOT_EXISTS_USERID(HttpStatus.NOT_FOUND, "존재하지 않는 사용자 ID입니다.", 404),

    DUPLICATION_USERID_ERROR(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다.", 400),

    // 이메일 인증 중복
    AUTH_EMAIL_DUPLICATION_ERROR(HttpStatus.BAD_REQUEST, "이메일이 중복되었습니다.", 400),

    // 이메일 인증 코드 오류
    AUTH_EMAIL_AUTH_NUMBER_ERROR(HttpStatus.BAD_REQUEST, "이메일 인증코드를 잘못 입력하였습니다. 다시 시도해 주세요.", 400),

    // 인증 시간 만료
    AUTH_EMAIL_EXPIRED_ERROR(HttpStatus.BAD_REQUEST, "이메일 인증 시간이 만료되었습니다.", 400),

    // 잘못된 이메일 요청
    AUTH_EMAIL_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "이메일 인증을 다시 시도해주세요.", 400),

    // 사용자 잘못된 요청
    LOGIN_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "비밀번호 또는 아이디가 올바르지 않습니다.", 400),

    // 중복되는 아이디 이메일
    LOGIN_DUPLICATION_ERROR(HttpStatus.CONFLICT, "이미 존재하는 아이디 또는 이메일 입니다.", 409),

    /**
     * JWT Error Code
     */
    JWT_INVALID_ERROR(HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다.", 401),
    JWT_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다.", 401),
    JWT_SIGNATURE_ERROR(HttpStatus.UNAUTHORIZED, "JWT 서명이 일치하지 않습니다.", 401),
    JWT_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 JWT 에러가 발생했습니다.", 500),

    /**
     * Token Error Code
     */
    TOKEN_INVALID_ERROR(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.", 401),

    // 패스워드 형식 오류
    PASSWORD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "패스워드 형식이 올바르지 않습니다.", 400),

    // 패스워드 일치 오류
    PASSWORD_MISS_MATCH_ERROR(HttpStatus.BAD_REQUEST, "패스워드 형식이 일치하지 않습니다.", 400),

    // 포토카드 게시물 조회 오류
    NOT_EXISTS_PRODUCT(HttpStatus.BAD_REQUEST, "포토카드가 존재하지 않습니다.", 400),

    // 포토카드 사용자 불일치 오류
    NOT_MATCHES_USER_PRODUCT(HttpStatus.BAD_REQUEST, "글을 작성한 유저가 아닙니다.", 400),

    // 임시 저장 포토카드 조회 오류
    NOT_EXISTS_TEMPORARILY_PRODUCT(HttpStatus.BAD_REQUEST, "임시 저장 포토 카드가 존재하지 않습니다.", 400),

    // 잘못된 서버 요청
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "Bad Request Exception", 400),

    // @RequestBody 데이터 미 존재
    REQUEST_BODY_MISSING_ERROR(HttpStatus.BAD_REQUEST, "Required request body is missing", 400),

    // 유효하지 않은 타입
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "Invalid Type Value", 400),

    // Request Parameter 로 데이터가 전달되지 않을 경우
    MISSING_REQUEST_PARAMETER_ERROR(HttpStatus.BAD_REQUEST, "Missing Servlet RequestParameter Exception", 400),

    // 입력/출력 값이 유효하지 않음
    IO_ERROR(HttpStatus.BAD_REQUEST, "I/O Exception", 400),

    // JSON 파싱 실패
    JSON_PARSE_ERROR(HttpStatus.BAD_REQUEST, "JsonParseException", 400),

    // jackson.core processing error
    JACKSON_PROCESS_ERROR(HttpStatus.BAD_REQUEST, "com.fasterxml.jackson.core Exception", 400),

    // 권한이 없음
    FORBIDDEN_ERROR(HttpStatus.FORBIDDEN, "Forbidden Exception", 403),

    // 서버로 요청한 리소스가 존재하지 않음
    NOT_FOUND_ERROR(HttpStatus.FORBIDDEN, "Not Found Exception", 403),

    // NULL Point Exception 발생
    NULL_POINT_ERROR(HttpStatus.FORBIDDEN, "Null Point Exception", 403),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_ERROR(HttpStatus.FORBIDDEN, "handle Validation Exception", 403),

    // @RequestBody 및 @RequestParam, @PathVariable 값이 유효하지 않음
    NOT_VALID_HEADER_ERROR(HttpStatus.FORBIDDEN, "Header에 데이터가 존재하지 않는 경우", 403),


    // 서버가 처리 할 방법을 모르는 경우 발생
    INTERVAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error Exception", 500),

    // Transaction Insert Error
    INSERT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Insert Transaction Error Exception", 500),

    // Transaction Update Error
    UPDATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Update Transaction Error Exception", 500),

    // Transaction Delete Error
    DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Delete Transaction Error Exception", 500);

    private final HttpStatus httpStatus;
    private final String message;
    private final int resultCode;
    private static final Map<String, ExceptionCode> exceptionMap = new HashMap<>();

    static {
        for(ExceptionCode exceptionCode : ExceptionCode.values()) {
            exceptionMap.put(exceptionCode.getMessage(), exceptionCode);
        }
    }

    public static ExceptionCode getErrorResponseCode(String message) {
        return exceptionMap.getOrDefault(message, ExceptionCode.INTERVAL_SERVER_ERROR);
    }

}
