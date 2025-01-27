package org.toastit_v2.core.application.auth.token.port;

import org.toastit_v2.core.domain.auth.token.Token;

import java.util.Optional;

public interface TokenRepository {

    void save(final Token token);

    Optional<Token> findById(final String id);

    Optional<Token> findByAccessToken(final String accessToken);

    void deleteByUserId(final String userId);

}
