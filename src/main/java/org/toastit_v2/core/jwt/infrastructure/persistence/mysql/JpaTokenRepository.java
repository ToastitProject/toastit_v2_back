package org.toastit_v2.core.jwt.infrastructure.persistence.mysql;

import java.util.Optional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.toastit_v2.core.common.infrastructure.persistence.JpaBaseRepository;
import org.toastit_v2.core.jwt.infrastructure.persistence.mysql.custom.CustomTokenRepository;
import org.toastit_v2.core.jwt.infrastructure.persistence.mysql.entity.TokenEntity;

public interface JpaTokenRepository extends JpaBaseRepository<TokenEntity, Long>, CustomTokenRepository {

    Optional<TokenEntity> findByUserId(Long id);

    @Modifying
    @Query("DELETE FROM TokenEntity t WHERE t.accessToken = :token")
    void deleteByAccessToken(String token);
}
