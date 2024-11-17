package org.toastit_v2.core.jwt.application.service;

import org.toastit_v2.core.jwt.domain.Token;

public interface TokenService {

    void saveOrUpdate(Token token);

    void updateAccessToken(String refreshToken, String accessToken);

    void deleteAccessToken(String token);
}
