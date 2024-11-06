package org.toastit_v2.feature.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.toastit_v2.common.infra.config.security.core.CustomUserDetails;
import org.toastit_v2.feature.controller.user.response.UserResponse;
import org.toastit_v2.feature.domain.user.User;
import org.toastit_v2.feature.service.user.UserService;
import org.toastit_v2.feature.service.user.port.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User getByEmail(String email) {
        return repository.getByEmail(email);
    }

    @Override
    public UserResponse getByUserDetails(CustomUserDetails userDetails) {

        User user = getByEmail(userDetails.getEmail());

        return UserResponse.from(user);
    }
}
