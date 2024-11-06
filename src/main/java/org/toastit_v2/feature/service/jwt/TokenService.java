package org.toastit_v2.feature.service.jwt;

import org.toastit_v2.feature.domain.jwt.Token;

public interface TokenService {

    void saveOrUpdate(Token token);

    void deleteAccessToken(String token);
}
