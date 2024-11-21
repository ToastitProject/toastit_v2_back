package org.toastit_v2.feature.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.exception.RestApiException;
import org.toastit_v2.core.common.application.util.JwtTokenizer;
import org.toastit_v2.core.jwt.application.service.TokenService;
import org.toastit_v2.core.security.domain.CustomUserDetails;
import org.toastit_v2.feature.user.application.port.UserRepository;
import org.toastit_v2.feature.user.domain.User;
import org.toastit_v2.feature.user.domain.UserLoginInfo;
import org.toastit_v2.feature.user.web.request.UserLoginRequest;

@Service
@Transactional
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;

    @Override
    public UserLoginInfo login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
        );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RestApiException(CommonExceptionCode.NOT_MATCH_AUTH_CODE);
        }

        String accessToken = jwtTokenizer.createAccessToken(user.getId(), user.getEmail(), user.getNickname(), user.getAuthority());
        String refreshToken = jwtTokenizer.createRefreshToken(user.getId(), user.getEmail(), user.getNickname(), user.getAuthority());

        tokenService.save(user.getId(), refreshToken);

        return UserLoginInfo.from(user.getId(), user.getNickname(), accessToken, refreshToken);
    }

    @Override
    public void logout(CustomUserDetails userDetails) {
        tokenService.deleteById(userDetails.getUserId());
    }

}
