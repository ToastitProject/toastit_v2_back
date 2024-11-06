package org.toastit_v2.feature.service.user;

import org.toastit_v2.common.infra.config.security.core.CustomUserDetails;
import org.toastit_v2.feature.controller.user.response.UserResponse;
import org.toastit_v2.feature.domain.user.User;

public interface UserService {

    User getByEmail(String email);

    UserResponse getByUserDetails(CustomUserDetails userDetails);
}
