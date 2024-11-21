package org.toastit_v2.core.jwt.application.port;

import java.util.Optional;

public interface TokenRepository {

    Optional<String> findById(String userId);

    void save(String userId, String refreshToken);

    void deleteById(String userId);
}
