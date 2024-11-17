package org.toastit_v2.core.jwt.application.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.exception.RestApiException;
import org.toastit_v2.core.jwt.domain.Token;
import org.toastit_v2.core.jwt.application.port.TokenRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private static final String EXISTING_TOKEN_UPDATE_ERROR = "기존 토큰 엔티티";
    private static final String REFRESH_TOKEN_UPDATE_ERROR = "리프레시 토큰";

    private final TokenRepository repository;

    @Override
    public void saveOrUpdate(Token token) {
        Optional<Token> oldToken = repository.findByUserId(token.getUserId());
        oldToken.ifPresentOrElse(
                existingToken -> {
                    existingToken.update(token);
                    checkUpdateResult(repository.update(existingToken), existingToken, EXISTING_TOKEN_UPDATE_ERROR);
                },
                () -> repository.save(token)
        );
    }

    @Override
    public void updateAccessToken(String refreshToken, String accessToken) {
        checkUpdateResult(repository.updateByRefreshToken(refreshToken, accessToken), refreshToken, REFRESH_TOKEN_UPDATE_ERROR );
    }

    @Override
    public void deleteAccessToken(String token) {
        repository.deleteByAccessToken(token);
    }

    private void checkUpdateResult(Long updateResult, Object tokenInfo, String tokenType) {
        if (updateResult < 0) {
            log.error("{} '{}' 업데이트 실패", tokenType, tokenInfo);
            throw new RestApiException(
                    tokenType.equals(EXISTING_TOKEN_UPDATE_ERROR) ? CommonExceptionCode.UPDATE_FAIL_TOKEN : CommonExceptionCode.UPDATE_FAIL_ACCESS_TOKEN
            );
        }
    }

}
