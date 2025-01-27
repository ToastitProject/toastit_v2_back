package org.toastit_v2.core.application.auth.token.port;

import io.jsonwebtoken.Claims;
import org.toastit_v2.common.type.auth.jwt.TokenStatus;

import javax.crypto.SecretKey;

public interface JwtInspector {

    TokenStatus getTokenStatus(final String token, final SecretKey secretKey);

    Claims parseToken(final String token, final SecretKey secretKey);

}
