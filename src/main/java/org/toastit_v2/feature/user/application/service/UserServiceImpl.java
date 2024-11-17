package org.toastit_v2.feature.user.application.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.toastit_v2.core.security.domain.CustomUserDetails;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.exception.RestApiException;
import org.toastit_v2.feature.user.application.port.UserRepository;
import org.toastit_v2.feature.user.web.response.UserResponse;
import org.toastit_v2.feature.user.domain.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public UserResponse getByUserDetails(CustomUserDetails userDetails) {
        User user = findByEmail(userDetails.getEmail())
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
                );

        return UserResponse.from(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return repository.findByNickname(nickname);
    }

}
