package org.toastit_v2.feature.service.jwt.port;

import java.util.Optional;
import org.toastit_v2.feature.domain.jwt.Token;

public interface TokenRepository {

    Token save(Token token);

    void update(Token token);

    void updateByAccessToken(String refreshToken, String accessToken);

    void deleteByAccessToken(String token);

    Optional<Token> findByUserId(Long id);
}
