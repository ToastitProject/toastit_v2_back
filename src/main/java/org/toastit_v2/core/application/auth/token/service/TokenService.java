package org.toastit_v2.core.application.auth.token.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.toastit_v2.core.domain.member.Member;

public interface TokenService {

    String createAccessToken(final Member member);

    String resolveAccessToken(final HttpServletRequest request);

    boolean validateAccessToken(final String accessToken);

    Authentication getAuthenticationFrom(final String accessToken);

    void logout(final Member member);

}
