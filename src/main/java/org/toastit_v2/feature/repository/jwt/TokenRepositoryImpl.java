package org.toastit_v2.feature.repository.jwt;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.toastit_v2.feature.domain.jwt.Token;
import org.toastit_v2.feature.model.entity.jwt.TokenEntity;
import org.toastit_v2.feature.service.jwt.port.TokenRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final JpaTokenRepository repository;

    @Override
    public Token save(Token token) {
        return repository.save(token.toEntity()).toModel();
    }

    @Override
    public Long update(Token token) {
        return repository.update(token);
    }

    @Override
    public Long updateByRefreshToken(String refreshToken, String accessToken) {
        return repository.updateByRefreshToken(refreshToken, accessToken);
    }

    @Override
    public void deleteByAccessToken(String token) {
        repository.deleteByAccessToken(token);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Token> findByUserId(Long userId) {
        return repository.findByUserId(userId)
                .map(TokenEntity::toModel);
    }

}
