package org.toastit_v2.feature.user.application.port;

import java.util.Optional;
import org.toastit_v2.feature.user.domain.User;
import org.toastit_v2.feature.user.domain.UserStatus;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    User getUserById(Long id);

    void save(User user);

    void update(long id, UserStatus status);

}
