package org.toastit_v2.common.config.auth.token;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * 토큰 인증 과정에서 접근이 거부된 경우를 처리하는 핸들러 클래스입니다.
 */
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 접근이 거부된 요청을 처리합니다.
     *
     * 이 메서드는 HTTP 응답 상태를 403 (Forbidden)으로 설정하여 클라이언트에게
     * 접근 권한이 없을 알립니다.
     *
     * @param request 현재 HTTP 요청
     * @param response 현재 HTTP 응답
     * @param accessDeniedException 접근이 거부되었을 때 발생한 예외
     * @throws IOException I/O 관련 예외가 발생한 경우
     * @throws ServletException 서블릿 처리 중 예외가 발생한 경우
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

}
