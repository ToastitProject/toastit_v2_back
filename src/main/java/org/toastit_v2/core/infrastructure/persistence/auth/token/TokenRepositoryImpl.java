package org.toastit_v2.core.infrastructure.persistence.auth.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.toastit_v2.core.application.auth.token.port.TokenRepository;
import org.toastit_v2.core.domain.auth.token.Token;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenCrudRepository repository;

    @Override
    public void save(final Token token) {
        repository.save(token);
    }

    @Override
    public Optional<Token> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Token> findByAccessToken(String accessToken) {
        return repository.findByAccessToken(accessToken);
    }

    @Override
    public void deleteByUserId(final String userId) {
        repository.deleteById(userId);
    }

}
