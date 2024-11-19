package org.toastit_v2.feature.user.infrastructure.persistence.mysql;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.toastit_v2.feature.user.domain.UserStatus;
import org.toastit_v2.feature.user.infrastructure.persistence.mysql.custom.CustomUserRepository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long>, CustomUserRepository {

    Optional<UserEntity> findByIdAndStatus(Long id, UserStatus status);

    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus status);

    Optional<UserEntity> findByNicknameAndStatus(String nickname, UserStatus status);

    boolean existsByEmail(String email);

}
