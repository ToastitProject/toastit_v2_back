package org.toastit_v2.common.jwt.application.port;

import java.util.Optional;

public interface TokenRepository {

    Optional<String> findById(String userId);

    void save(String userId, String refreshToken);

    void deleteById(String userId);
}
