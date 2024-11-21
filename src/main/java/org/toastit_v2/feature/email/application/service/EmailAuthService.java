package org.toastit_v2.feature.email.application.service;

import org.toastit_v2.feature.email.web.request.EmailAuthRequest;

public interface EmailAuthService {

    void sendAuthMail(EmailAuthRequest request);

    void checkDuplicate(String email);

    void verifyEmailWithCode(String email, String code);

}
