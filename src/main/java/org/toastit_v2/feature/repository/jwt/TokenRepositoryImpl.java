package org.toastit_v2.feature.repository.jwt;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.toastit_v2.common.response.code.CommonExceptionCode;
import org.toastit_v2.common.response.exception.RestApiException;
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
    public void update(Token token) {
        repository.update(token);
    }

    @Override
    public void updateByAccessToken(String refreshToken, String accessToken) {

        if (repository.updateByRefreshToken(refreshToken, accessToken) < 0) {
            log.error("리프레시 토큰 '{}'에 대한 액세스 토큰 업데이트 실패", refreshToken);
            throw new RestApiException(CommonExceptionCode.JWT_UNKNOWN_ERROR);
        }
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
