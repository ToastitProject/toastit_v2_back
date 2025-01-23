package org.toastit_v2.feature.user.application.service;

import jakarta.servlet.http.HttpServletRequest;
import org.toastit_v2.common.security.domain.CustomUserDetails;
import org.toastit_v2.feature.user.domain.User;
import org.toastit_v2.feature.user.web.request.UserUpdateNicknameRequest;
import org.toastit_v2.feature.user.web.request.UserUpdatePasswordRequest;

public interface UserInfoService {

    User getUserByDetails(CustomUserDetails userDetails);

    void checkDuplicate(String nickname);

    void update(CustomUserDetails userDetails, UserUpdateNicknameRequest request);

    void update(CustomUserDetails userDetails, UserUpdatePasswordRequest request);

    void deactivate(CustomUserDetails userDetails, HttpServletRequest request);

}
