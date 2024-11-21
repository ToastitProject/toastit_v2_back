package org.toastit_v2.feature.repository.jwt.custom;

import org.toastit_v2.feature.domain.jwt.Token;

public interface CustomTokenRepository {

    Long update(Token token);

    Long updateByRefreshToken(String refreshToken, String accessToken);

}