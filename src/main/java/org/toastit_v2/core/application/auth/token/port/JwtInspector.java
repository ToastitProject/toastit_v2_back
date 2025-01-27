package org.toastit_v2.core.application.auth.token.port;

import io.jsonwebtoken.Claims;
import org.toastit_v2.common.type.auth.jwt.TokenStatus;

import javax.crypto.SecretKey;

public interface JwtInspector {

    TokenStatus getTokenStatus(String token, SecretKey secretKey);

    Claims parseToken(String token, SecretKey secretKey);

}
