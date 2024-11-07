package org.toastit_v2.feature.service.user.port;

import java.util.Optional;
import org.toastit_v2.feature.domain.user.User;

public interface UserRepository {

    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);

}
