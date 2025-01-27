package org.toastit_v2.core.infrastructure.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.toastit_v2.common.exception.custom.CustomJwtException;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.common.type.auth.jwt.TokenStatus;
import org.toastit_v2.core.application.auth.token.port.JwtInspector;

import javax.crypto.SecretKey;

@Slf4j
@Component
public class JwtHandler implements JwtInspector {

    @Override
    public TokenStatus getTokenStatus(final String token, final SecretKey secretKey) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return TokenStatus.AUTHENTICATED;
        } catch (ExpiredJwtException | IllegalArgumentException e) {
            log.error(ExceptionCode.JWT_EXPIRED_ERROR.getMessage());
            return TokenStatus.EXPIRED;
        } catch (JwtException e) {
            log.error(e.getMessage());
            throw new CustomJwtException(ExceptionCode.JWT_UNKNOWN_ERROR);
        }
    }

    @Override
    public Claims parseToken(final String token, final SecretKey secretKey) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            log.error(e.getMessage());
            throw new CustomJwtException(ExceptionCode.JWT_INVALID_ERROR);
        }
    }

}
