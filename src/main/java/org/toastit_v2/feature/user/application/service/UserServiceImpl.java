package org.toastit_v2.feature.user.application.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.exception.RestApiException;
import org.toastit_v2.feature.user.application.port.UserRepository;
import org.toastit_v2.feature.email.application.service.EmailAuthService;
import org.toastit_v2.feature.email.application.service.EmailCodeService;
import org.toastit_v2.feature.user.domain.UserCreate;
import org.toastit_v2.feature.user.web.request.UserJoinRequest;
import org.toastit_v2.feature.user.domain.User;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EmailAuthService emailAuthService;
    private final EmailCodeService emailCodeService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    @Override
    public void save(UserJoinRequest request) {
        findByEmail(request.getEmail()).ifPresent(user -> {
            throw new RestApiException(CommonExceptionCode.EXIST_EMAIL_ERROR);
        });

        emailAuthService.verifyEmailWithCode(request.getEmail(), request.getAuthCode());

        emailCodeService.delete(request.getEmail());

        User user = UserCreate.from(request, passwordEncoder).toUser();
        userRepository.save(user);
    }

}
