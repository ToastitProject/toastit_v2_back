package org.toastit_v2.core.infrastructure.persistence.auth.mail;

import org.springframework.data.repository.CrudRepository;
import org.toastit_v2.common.annotation.jpa.ExcludeJpaRepository;
import org.toastit_v2.core.domain.auth.mail.AuthMail;

import java.util.Optional;

@ExcludeJpaRepository
public interface AuthMailCrudRepository extends CrudRepository<AuthMail, String> {
}
