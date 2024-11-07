package org.toastit_v2.feature.service.user;

import java.util.Optional;
import org.toastit_v2.common.infra.config.security.core.CustomUserDetails;
import org.toastit_v2.feature.controller.user.response.UserResponse;
import org.toastit_v2.feature.domain.user.User;

public interface UserService {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    UserResponse getByUserDetails(CustomUserDetails userDetails);

}
