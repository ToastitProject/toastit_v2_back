package org.toastit_v2.common.config.auth.token;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.toastit_v2.core.application.auth.token.service.TokenService;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = tokenService.resolveAccessToken(request);
        if (tokenService.validateAccessToken(accessToken)) {
            this.setAuthenticationToContext(accessToken);
            filterChain.doFilter(request, response);
            return;
        }

        log.debug("Access Token을 찾을 수 없습니다.");
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationToContext(String accessToken) {
        Authentication authentication = tokenService.getAuthenticationFromAccessToken(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
