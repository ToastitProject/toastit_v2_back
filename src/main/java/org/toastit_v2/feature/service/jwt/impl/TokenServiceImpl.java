package org.toastit_v2.feature.service.jwt.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.toastit_v2.feature.domain.jwt.Token;
import org.toastit_v2.feature.service.jwt.TokenService;
import org.toastit_v2.feature.service.jwt.port.TokenRepository;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository repository;

    @Override
    public void saveOrUpdate(Token token) {

        Optional<Token> oldToken = repository.findByUserId(token.getUserId());

        oldToken.ifPresentOrElse(
                existingToken -> {
                    existingToken.update(token);
                    repository.save(existingToken);
                },
                () -> repository.save(token)
        );
    }

    public void updateAccessToken(String refreshToken, String accessToken) {
        repository.updateByAccessToken(refreshToken, accessToken);
    }

    @Override
    public void deleteAccessToken(String token) {
        repository.deleteByAccessToken(token);
    }
}
