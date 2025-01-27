package org.toastit_v2.core.application.member.service;

import org.toastit_v2.core.domain.member.Member;
import org.toastit_v2.core.ui.member.payload.request.LoginRequest;
import org.toastit_v2.core.ui.member.payload.request.SignUpRequest;

public interface MemberService {

    Member signUp(SignUpRequest request);

    void checkUserIdDuplicate(String userId);

    Member findByUserId(String userId);

    String login(LoginRequest request);

    void logout(String userId);

}
