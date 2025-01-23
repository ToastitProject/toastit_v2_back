package org.toastit_v2.common.common.application.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.toastit_v2.common.security.domain.Authority;
import org.toastit_v2.common.common.application.code.CommonExceptionCode;
import org.toastit_v2.common.common.application.exception.RestApiException;

@Component
@Validated
public class JwtTokenizer {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;

    private final Long accessTokenExpire;
    private final Long refreshTokenExpire;

    public JwtTokenizer(
            @NotNull @Value("${jwt.access.secret}") String accessSecretKey,
            @NotNull @Value("${jwt.refresh.secret}") String refreshSecretKey,
            @NotNull @Value("${jwt.access.expire}") Long accessTokenExpire,
            @NotNull @Value("${jwt.refresh.expire}") Long refreshTokenExpire
    ) {
        this.accessSecretKey = Keys.hmacShaKeyFor(accessSecretKey.getBytes(StandardCharsets.UTF_8));
        this.refreshSecretKey = Keys.hmacShaKeyFor(refreshSecretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpire = accessTokenExpire;
        this.refreshTokenExpire = refreshTokenExpire;
    }

    public String createAccessToken(Long userId, String email, String nickname, Authority authority) {
        return createToken(userId, email, nickname, authority, accessTokenExpire, accessSecretKey);
    }

    public String createRefreshToken(Long userId, String email, String nickname, Authority authority) {
        return createToken(userId, email, nickname, authority, refreshTokenExpire, refreshSecretKey);
    }

    public Claims parseAccessToken(String accessToken) {
        return parseToken(accessToken, accessSecretKey);
    }

    public Claims parseRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecretKey);
    }

    public String findAccessTokenFrom(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String createToken(Long userId,
                               String email,
                               String nickname,
                               Authority authority,
                               Long expire,
                               SecretKey secretKey
    ) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expire);

        Claims claims = Jwts.claims()
                .add("userId", userId)
                .add("nickname", nickname)
                .add("authority", authority)
                .build();

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiration)
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }

    private Claims parseToken(String token, SecretKey secretKey) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (SignatureException | MalformedJwtException e) {
            throw new RestApiException(CommonExceptionCode.JWT_INVALID_ERROR);
        }
    }

}
