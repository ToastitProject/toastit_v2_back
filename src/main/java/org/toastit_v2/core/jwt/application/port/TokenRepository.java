package org.toastit_v2.core.jwt.application.port;

import java.util.Optional;
import org.toastit_v2.core.jwt.domain.Token;

public interface TokenRepository {

    Token save(Token token);

    Long update(Token token);

    Long updateByRefreshToken(String refreshToken, String accessToken);

    void deleteByAccessToken(String token);

    Optional<Token> findByUserId(Long id);
}
