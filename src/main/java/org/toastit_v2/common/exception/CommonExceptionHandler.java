package org.toastit_v2.common.exception;

import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.bson.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.toastit_v2.common.exception.custom.*;
import org.toastit_v2.common.response.ExceptionResponse;
import org.toastit_v2.common.response.code.ExceptionCode;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    /**
     * HTTP 요청 본문이 잘못되었거나, @RequestBody가 누락된 경우를 나타냅니다.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Http Message Not Readable Exception", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.REQUEST_BODY_MISSING_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * API 호출 시, 객체 또는 파라미터 값이 유효하지 않은 경우를 나타냅니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Method Argument Not Valid Exception", ex);
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder.append(fieldError.getField()).append(":");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(", ");
        }
        final ExceptionResponse response = ExceptionResponse.ofFieldError(bindingResult, ExceptionCode.NOT_VALID_ERROR);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * HTTP 요청에서 필수 헤더가 누락된 경우 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    protected ResponseEntity<ExceptionResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.error("Missing Request Header Exception", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.NOT_VALID_HEADER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * HTTP 요청에서 필수 요청 파라미터가 누락된 경우 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ExceptionResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error("Missing Servlet Request Parameter", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.MISSING_REQUEST_PARAMETER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 클라이언트 요청 오류(예: 잘못된 요청, 권한 문제 등)로 인해 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<ExceptionResponse> handleHttpClientErrorException(HttpClientErrorException ex) {
        log.error("Http Client Error Exception", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.BAD_REQUEST_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 객체가 null 상태에서 호출되거나 접근이 시도될 때 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException ex) {
        log.error("Null Pointer Exception", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.NULL_POINT_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 파일 읽기/쓰기, 네트워크 통신 등 입출력 작업 중 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(IOException.class)
    protected ResponseEntity<ExceptionResponse> handleIOException(IOException ex) {
        log.error("IO Exception", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.IO_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Json 데이터를 파싱하는 과정에서 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(JsonParseException.class)
    protected ResponseEntity<ExceptionResponse> handleJsonParseException(JsonParseException ex) {
        log.error("Json Parse Exception", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.JSON_PARSE_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 이 클래스/메서드는 예외를 포괄적으로 처리하여 프로그램의 중단을 방지하고,
     * 필요한 경우 로깅하거나 사용자에게 적절한 메시지를 전달합니다.
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        log.error("Exception", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.INTERVAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 인증 이메일 전송이나 확인 과정에서 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(CustomAuthMailException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomAuthMailException(CustomAuthMailException ex) {
        log.error("Auth Mail Exception", ex);
        return buildErrorResponse(ex);
    }

    /**
     * 사용자 정의 칵테일 관련 작업 중 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(CustomCraftCocktailException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomCraftCocktailException(CustomCraftCocktailException ex) {
        log.error("Craft Cocktail Exception", ex);
        return buildErrorResponse(ex);
    }

    /**
     * 사용자 정의 칵테일 작성 중 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(CustomCraftCocktailException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomCraftCocktailEmptyRecipeTitleException(CustomCraftCocktailException ex) {
        log.error("Craft Cocktail Recipe Tile Empty Exception", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.EMPTY_RECIPE_TITLE, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 사용자 정의 칵테일 작성 중 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(CustomCraftCocktailException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomCraftCocktailEmptyRecipeDescriptionException(CustomCraftCocktailException ex) {
        log.error("Craft Cocktail Recipe Description Empty Exception", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.EMPTY_RECIPE_DESCRIPTION, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 사용자 정의 칵테일 작성 중 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(CustomCraftCocktailException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomCraftCocktailEmptyRecipeException(CustomCraftCocktailException ex) {
        log.error("Craft Cocktail Recipe Empty Exception", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.EMPTY_RECIPE, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 사용자 정의 칵테일 작성 중 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(CustomCraftCocktailException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomCraftCocktailEmptyIngredientException(CustomCraftCocktailException ex) {
        log.error("Craft Cocktail Recipe Empty Exception", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.EMPTY_INGREDIENTS, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 사용자 정의 칵테일 작성 중 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(CustomCraftCocktailException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomCraftCocktailUserNotFoundException(CustomCraftCocktailException ex) {
        log.error("Craft Cocktail User Not Found Exception", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.USER_NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 사용자 정의 칵테일 작성 중 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(CustomCraftCocktailException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomCraftRecipeSaveFailureException(CustomCraftCocktailException ex) {
        log.error("Craft Cocktail Recipe Save Fail Exception", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.RECIPE_SAVE_FAILURE, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 사용자 정의 칵테일 작성 중 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(CustomCraftCocktailException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomCraftUnnAuthorizedAccessException(CustomCraftCocktailException ex) {
        log.error("Craft Cocktail Unauthorized Access Exception", ex);
        final ExceptionResponse response = ExceptionResponse.create(ExceptionCode.UNAUTHORIZED_ACCESS, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * JWT(Json Web Token) 처리 과정에서 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(CustomJwtException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomJwtException(CustomJwtException ex) {
        log.error("JWT Exception", ex);
        return buildErrorResponse(ex);
    }

    /**
     * 멤버(Member) 관련 작업 중 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(CustomMemberException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomMemberException(CustomMemberException ex) {
        log.error("Member Exception", ex);
        return buildErrorResponse(ex);
    }

    /**
     * SSH 브리지 작업 중 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(CustomSshBridgeException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomSshBridgeException(CustomSshBridgeException ex) {
        log.error("SSH Bridge Exception", ex);
        return buildErrorResponse(ex);
    }

    /**
     * 토큰 처리 작업 중 발생하는 경우를 나타냅니다.
     */
    @ExceptionHandler(CustomTokenException.class)
    protected ResponseEntity<ExceptionResponse> handleCustomTokenException(CustomTokenException ex) {
        log.error("Token Exception", ex);
        return buildErrorResponse(ex);
    }

    private static <T extends RuntimeException> ResponseEntity<ExceptionResponse> buildErrorResponse(final T ex) {
        ExceptionCode exceptionCode = ExceptionCode.getErrorResponseCode(ex.getMessage());
        ExceptionResponse exceptionResponse = ExceptionResponse.create(exceptionCode, ex.getMessage() == null ? "empty" : ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, exceptionCode.getHttpStatus());
    }

}
