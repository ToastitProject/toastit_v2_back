package org.toastit_v2.common.response.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.toastit_v2.common.response.ExceptionResponse;
import org.toastit_v2.common.response.SuccessResponse;
import org.toastit_v2.common.response.code.CommonResponseCode;
import org.toastit_v2.common.response.handler.util.SuccessResponseUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "org.toastit_v2")
public class CommonSuccessHandler implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    public CommonSuccessHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object responsePayload, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response
    ) {
        HttpServletResponse httpServletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int statusCode = httpServletResponse.getStatus();

        if (isSuccessful(statusCode)) {
            CommonResponseCode responseCode = getResponseCodeByStatus(statusCode);

            if (shouldWrapBody(responsePayload)) {
                return SuccessResponseUtil.handleResponseForSuccess(responseCode, responsePayload).getBody();
            }

            if (responsePayload instanceof String) {
                return handleStringResponse((String) responsePayload, response);
            }
        }

        return responsePayload;
    }

    private boolean isSuccessful(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }

    private boolean shouldWrapBody(Object body) {
        return !(body instanceof SuccessResponse || body instanceof String || body instanceof ProblemDetail || body instanceof ExceptionResponse);
    }

    private CommonResponseCode getResponseCodeByStatus(int statusCode) {

        return Arrays.stream(CommonResponseCode.values())
                .filter(code -> code.getHttpStatus().value() == statusCode)
                .findFirst()
                .orElse(null);
    }

    private String handleStringResponse(String body, ServerHttpResponse response) {

        try {
            String jsonResponse = createJsonResponse("200 OK", "요청이 성공됐습니다", body, "SUCCESS");

            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            return jsonResponse;

        } catch (JsonProcessingException e) {
            log.error("JSON 변환 오류 : ", e);
            return "{\"status\":\"500 Internal Server Error\",\"msg\":\"응답 생성 오류\",\"code\":\"ERROR\"}";
        }
    }

    private String createJsonResponse(String status, String msg, Object data, String code) throws JsonProcessingException {

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("msg", msg);
        responseMap.put("data", data);
        responseMap.put("code", code);

        return objectMapper.writeValueAsString(responseMap);
    }
}