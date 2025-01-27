package org.toastit_v2.core.application.auth.mail.service;

import org.toastit_v2.core.ui.auth.mail.payload.request.AuthMailRequest;

public interface AuthMailService {

    void send(final AuthMailRequest request);

    void validateAuthMail(String userEmail, String authNumber);

}
