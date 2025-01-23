package org.toastit_v2.common.jwt.application.service;

import java.util.Optional;

public interface TokenService {

    Optional<String> findById(Long userId);

    void save(Long userId, String refreshToken);

    void deleteById(Long userId);

}
