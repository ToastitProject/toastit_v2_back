package org.toastit_v2.common.config.auth.token;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 토큰 인증 과정에서 인증되지 않은 요청을 처리하는 엔트리 포인트 클래스이다.
 */
public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 인증되지 않은 요청을 처리합니다.
     *
     * 이 메서드는 HTTP 응답 상태를 401 (Unauthorized)로 설정하고,
     * 클라이언트에게 인증이 필요함을 알리는 메세지를 전송합니다.
     *
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
