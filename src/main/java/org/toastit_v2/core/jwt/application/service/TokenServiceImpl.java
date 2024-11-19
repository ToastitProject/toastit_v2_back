package org.toastit_v2.core.jwt.application.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.toastit_v2.core.jwt.application.port.TokenRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository repository;

    @Override
    public Optional<String> findById(Long userId) {
        return repository.findById(String.valueOf(userId));
    }

    @Override
    public void save(Long userId, String refreshToken) {
        repository.save(String.valueOf(userId), refreshToken);
    }

    @Override
    public void deleteById(Long userId) {
        repository.deleteById(String.valueOf(userId));
    }

}
