package org.toastit_v2.common.jwt.application.service;

import org.toastit_v2.common.jwt.domain.Token;
import org.toastit_v2.common.jwt.web.request.TokenRefreshRequest;

public interface TokenRefreshService {

    Token refreshAccessToken(TokenRefreshRequest request);

}
