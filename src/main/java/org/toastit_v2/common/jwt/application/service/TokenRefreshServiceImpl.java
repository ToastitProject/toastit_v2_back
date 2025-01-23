package org.toastit_v2.common.jwt.application.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.toastit_v2.common.common.application.code.CommonExceptionCode;
import org.toastit_v2.common.common.application.exception.RestApiException;
import org.toastit_v2.common.common.application.util.JwtTokenizer;
import org.toastit_v2.common.jwt.domain.Token;
import org.toastit_v2.common.jwt.web.request.TokenRefreshRequest;
import org.toastit_v2.common.security.domain.Authority;
import org.toastit_v2.feature.user.application.port.UserRepository;
import org.toastit_v2.feature.user.domain.User;

@Service
@RequiredArgsConstructor
public class TokenRefreshServiceImpl implements TokenRefreshService {

    private final TokenService tokenService;

    private final UserRepository userRepository;

    private final JwtTokenizer jwtTokenizer;

    @Override
    public Token refreshAccessToken(TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken);
        Long userId = claims.get("userId", Long.class);
        Authority authority = Authority.valueOf(claims.get("authority", String.class));

        User user = userRepository.getUserById(userId);

        String storedRefreshToken = tokenService.findById(userId).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_REFRESH_TOKEN)
        );

        if (!storedRefreshToken.equals(refreshToken)) {
            tokenService.deleteById(userId);
            throw new RestApiException(CommonExceptionCode.JWT_INVALID_ERROR);
        }

        String accessToken = jwtTokenizer.createAccessToken(userId, user.getEmail(), user.getNickname(), authority);
        String newRefreshToken = jwtTokenizer.createRefreshToken(userId, user.getEmail(), user.getNickname(), authority);
        tokenService.save(userId, newRefreshToken);

        return Token.from(user.getId(), user.getNickname(), accessToken, refreshToken);
    }

}
