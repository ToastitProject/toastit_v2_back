package org.toastit_v2.feature.user.application.service;

import java.util.Optional;
import org.toastit_v2.core.security.domain.CustomUserDetails;
import org.toastit_v2.feature.user.web.response.UserResponse;
import org.toastit_v2.feature.user.domain.User;

public interface UserService {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    UserResponse getByUserDetails(CustomUserDetails userDetails);
}
