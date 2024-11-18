package org.toastit_v2.feature.user.application.port;

import java.util.Optional;
import org.toastit_v2.feature.user.domain.User;

public interface UserRepository {

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);
}
