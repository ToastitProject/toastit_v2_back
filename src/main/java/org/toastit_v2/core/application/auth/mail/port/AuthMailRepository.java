package org.toastit_v2.core.application.auth.mail.port;

import org.toastit_v2.core.domain.auth.mail.AuthMail;

import java.util.Optional;

public interface AuthMailRepository {

    void save(final AuthMail authMail);

    Optional<AuthMail> findById(String userEmail);

}
