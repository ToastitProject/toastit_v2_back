package org.toastit_v2.feature.repository.user;

import java.util.Optional;
import org.toastit_v2.feature.model.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity>  findByEmail(String email);

    Optional<UserEntity> findByNickname(String nickname);

}
