package org.toastit_v2.feature.user.infrastructure.persistence.mysql.custom;

import org.toastit_v2.feature.user.domain.UserStatus;

public interface CustomUserRepository {

    long update(Long userId, UserStatus status);

}
