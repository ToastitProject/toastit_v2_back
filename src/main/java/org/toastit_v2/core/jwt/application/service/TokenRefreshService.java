package org.toastit_v2.core.jwt.application.service;

import org.toastit_v2.core.jwt.domain.Token;
import org.toastit_v2.core.jwt.web.request.TokenRefreshRequest;

public interface TokenRefreshService {

    Token refreshAccessToken(TokenRefreshRequest request);

}
