package org.toastit_v2.feature.service.user.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.toastit_v2.common.infra.config.security.core.CustomUserDetails;
import org.toastit_v2.common.response.code.CommonExceptionCode;
import org.toastit_v2.common.response.exception.RestApiException;
import org.toastit_v2.feature.controller.user.response.UserResponse;
import org.toastit_v2.feature.domain.user.User;
import org.toastit_v2.feature.service.user.UserService;
import org.toastit_v2.feature.service.user.port.UserRepository;

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
