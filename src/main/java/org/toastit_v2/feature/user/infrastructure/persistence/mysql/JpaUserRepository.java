package org.toastit_v2.feature.user.infrastructure.persistence.mysql;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.toastit_v2.feature.user.infrastructure.persistence.mysql.entity.UserEntity;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity>  findByEmail(String email);

    Optional<UserEntity> findByNickname(String nickname);
}
