package org.toastit_v2.core.jwt.infrastructure.persistence.mysql.custom;

import org.toastit_v2.core.jwt.domain.Token;

public interface CustomTokenRepository {

    Long update(Token token);

    Long updateByRefreshToken(String refreshToken, String accessToken);
}
