package org.toastit_v2.core.application.auth.token.port;

import org.toastit_v2.core.domain.auth.token.Token;

import java.util.Optional;

public interface TokenRepository {

    void save(Token token);

    Optional<Token> findById(String userId);

    Optional<Token> findByAccessToken( String accessToken);

    void deleteById(String userId);

}
