package org.toastit_v2.common.infra.config.security.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.toastit_v2.common.response.code.CommonExceptionCode;
import org.toastit_v2.common.response.handler.util.ExceptionResponseUtil;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(
                new ObjectMapper().writeValueAsString(
                        ExceptionResponseUtil.handleResponseForException(CommonExceptionCode.FORBIDDEN).getBody()
                )
        );
    }
}
