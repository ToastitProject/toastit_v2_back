package org.toastit_v2.core.application.auth.token.service;

import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.toastit_v2.common.generator.auth.jwt.JwtGenerator;
import org.toastit_v2.core.domain.auth.token.TokenStatus;
import org.toastit_v2.core.application.auth.security.service.UserDetailsServiceImpl;
import org.toastit_v2.core.application.auth.token.port.TokenRepository;
import org.toastit_v2.core.application.auth.token.port.JwtInspector;
import org.toastit_v2.core.domain.auth.token.Token;
import org.toastit_v2.core.domain.member.Member;

import javax.crypto.SecretKey;

@Slf4j
@Service
@Validated
public class TokenServiceImpl implements TokenService {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final SecretKey accessKey;
    private final long accessKeyExpire;

    private final JwtGenerator jwtGenerator;
    private final JwtInspector jwtInspector;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    public TokenServiceImpl(
            @NotNull @Value("${jwt.access.secret}") final String accessSecretKey,
            @NotNull @Value("${jwt.access.expire}") final long accessExpire,
            JwtGenerator jwtGenerator,
            JwtInspector jwtInspector,
            UserDetailsServiceImpl userDetailsService,
            TokenRepository tokenRepository
    ) {
        this.accessKey = Keys.hmacShaKeyFor(accessSecretKey.getBytes());
        this.accessKeyExpire = accessExpire;
        this.jwtGenerator = jwtGenerator;
        this.jwtInspector = jwtInspector;
        this.userDetailsService = userDetailsService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    @Transactional
    public String createAccessToken(final Member member) {
        final String accessToken = jwtGenerator.generate(accessKey, accessKeyExpire, member);
        tokenRepository.save(Token.create(member.getUserId(), accessToken));
        return accessToken;
    }

    public void save(final Member member, final String accessToken) {
        tokenRepository.save(Token.create(member.getUserId(), accessToken));
    }

    @Override
    public String resolveAccessToken(final HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateAccessToken(final String accessToken) {
        TokenStatus tokenStatus = jwtInspector.getTokenStatus(accessToken, accessKey);
        log.debug(tokenStatus.toString());
        return tokenStatus == TokenStatus.AUTHENTICATED && tokenRepository.findById(getUserIdFrom(accessToken, accessKey)).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public Authentication getAuthenticationFrom(final String accessToken) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(getUserIdFrom(accessToken, accessKey));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserIdFrom(final String token, final SecretKey secretKey) {
        return jwtInspector.parseToken(token, secretKey).getSubject();
    }

    @Override
    @Transactional
    public void logout(final Member member) {
        tokenRepository.deleteById(member.getUserId());
    }

}
