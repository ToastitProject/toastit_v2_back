package org.toastit_v2.core.infrastructure.persistence.auth.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.toastit_v2.core.application.auth.mail.port.AuthMailRepository;
import org.toastit_v2.core.domain.auth.mail.AuthMail;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthMailRepositoryImpl implements AuthMailRepository {

    private final AuthMailCrudRepository repository;

    @Override
    public void save(final AuthMail authMail) {
        repository.save(authMail);
    }

    @Override
    public Optional<AuthMail> findById(final String userEmail) {
        return repository.findById(userEmail);
    }

}
