package org.toastit_v2.feature.service.user.port;

import org.toastit_v2.feature.domain.user.User;

public interface UserRepository {

    User getByUsername(String nickname);

    User getByEmail(String email);
}
