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
     *  Image Exception Code
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
     * Base Cocktail Exception Code
     */

    /**
     * Craft Cocktail Exception Code
     */
    FAIL_CREATE_CRAFT_COCKTAIL(HttpStatus.BAD_REQUEST, "커스텀 칵테일 생성 실패하였습니다.", 400),

    /**
     * Member Exception Code
     */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.", 404),
    MEMBER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "사용자 접근이 거부되었습니다.", 403),
    MEMBER_DUPLICATE(HttpStatus.CONFLICT, "이미 존재하는 회원입니다.", 409),
    PASSWORD_MISS_MATCH_ERROR(HttpStatus.BAD_REQUEST, "패스워드 형식이 일치하지 않습니다.", 400),
    NOT_EXISTS_USERID(HttpStatus.NOT_FOUND, "존재하지 않는 사용자 ID입니다.", 404),

    /**
     * Auth - Mail Exception Code
     */
    AUTH_EMAIL_DUPLICATION_ERROR(HttpStatus.BAD_REQUEST, "이메일이 중복되었습니다.", 400),
    AUTH_EMAIL_AUTH_NUMBER_ERROR(HttpStatus.BAD_REQUEST, "이메일 인증코드를 잘못 입력하였습니다. 다시 시도해 주세요.", 400),
    AUTH_EMAIL_EXPIRED_ERROR(HttpStatus.BAD_REQUEST, "이메일 인증 시간이 만료되었습니다.", 400),
    AUTH_EMAIL_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "이메일 인증을 다시 시도해주세요.", 400),

    /**
     * JWT Error Code
     */
    JWT_INVALID_ERROR(HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다.", 401),
    JWT_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다.", 401),
    JWT_SIGNATURE_ERROR(HttpStatus.UNAUTHORIZED, "JWT 서명이 일치하지 않습니다.", 401),
    JWT_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 JWT 에러가 발생했습니다.", 500),

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
