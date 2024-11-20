package org.toastit_v2.feature.user.application.service;

import org.toastit_v2.core.security.domain.CustomUserDetails;
import org.toastit_v2.feature.user.domain.UserLoginInfo;
import org.toastit_v2.feature.user.web.request.UserLoginRequest;

public interface UserAuthService {

    UserLoginInfo login(UserLoginRequest request);

    void logout(CustomUserDetails userDetails);

}
