package org.toastit_v2.core.infrastructure.persistence.auth.token;

import org.springframework.data.repository.CrudRepository;
import org.toastit_v2.common.annotation.jpa.ExcludeJpaRepository;
import org.toastit_v2.core.domain.auth.token.Token;

@ExcludeJpaRepository
public interface TokenCrudRepository extends CrudRepository<Token, String> {
}
