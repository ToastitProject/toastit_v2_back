package org.toastit_v2.common.infra.config.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.toastit_v2.common.infra.config.security.core.Authority;
import org.toastit_v2.common.infra.config.security.core.CustomUserDetails;
import org.toastit_v2.common.infra.config.security.token.AuthenticationToken;
import org.toastit_v2.common.response.code.CommonExceptionCode;
import org.toastit_v2.common.response.exception.RestApiException;
import org.toastit_v2.common.util.JwtTokenizer;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenizer jwtTokenizer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtTokenizer.findAccessTokenFromRequest(request);
        log.debug("요청에서 추출한 액세스 토큰 : {}", accessToken);

        if (StringUtils.hasText(accessToken)) {
            try {
                getAuthentication(accessToken);
                log.debug("액세스 토큰 인증 성공 : {}", accessToken);
            } catch (Exception e) {
                handleAuthenticationException(e, accessToken);
            }
        } else {
            log.warn("요청에 액세스 토큰이 없습니다.");
        }

        filterChain.doFilter(request, response);
    }

    private void getAuthentication(String accessToken) {

        Claims claims = jwtTokenizer.parseAccessToken(accessToken);
        String email = claims.getSubject();
        String nickname = claims.get("nickname", String.class);
        Authority authority = Authority.valueOf(claims.get("authority", String.class));

        List<Authority> authorities = Collections.singletonList(authority);

        CustomUserDetails userDetails = new CustomUserDetails(email, "", nickname, authorities);
        Authentication authentication = new AuthenticationToken(authorities, userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void handleAuthenticationException(Exception e, String accessToken) {

        String exceptionType = e.getClass().getSimpleName();

        switch (exceptionType) {
            case "ExpiredJwtException":
                log.warn("만료된 JWT 토큰 : {}", accessToken, e);
                throw new RestApiException(CommonExceptionCode.JWT_UNKNOWN_ERROR);
            case "UnsupportedJwtException":
                log.error("만료된 JWT 토큰 : {}", accessToken, e);
                throw new RestApiException(CommonExceptionCode.JWT_UNKNOWN_ERROR);
            case "MalformedJwtException":
                log.error("잘못된 형식의 JWT 토큰 : {}", accessToken, e);
                throw new RestApiException(CommonExceptionCode.JWT_INVALID_ERROR);
            case "IllegalArgumentException":
                log.error("잘못된 인자 : {}", accessToken, e);
                throw new RestApiException(CommonExceptionCode.JWT_UNKNOWN_ERROR);
            default:
                log.error("토큰 처리 중 내부 오류 발생 : {}", accessToken, e);
                throw new RestApiException(CommonExceptionCode.JWT_INTERNAL_ERROR);
        }
    }
}
