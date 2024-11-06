package org.toastit_v2.feature.repository.jwt;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.toastit_v2.feature.model.entity.jwt.TokenEntity;
import org.toastit_v2.feature.repository.jwt.custom.CustomTokenRepository;

public interface JpaTokenRepository extends JpaRepository<TokenEntity, Long>, CustomTokenRepository {

    Optional<TokenEntity> findByUserId(Long id);

    @Modifying
    @Query("DELETE FROM TokenEntity t WHERE t.accessToken = :token")
    void deleteByAccessToken(String token);
}
