package org.toastit_v2.common.jwt.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.toastit_v2.common.common.application.code.CommonExceptionCode;
import org.toastit_v2.common.common.application.config.swagger.ExceptionCodeAnnotations;
import org.toastit_v2.common.jwt.application.service.TokenRefreshService;
import org.toastit_v2.common.jwt.web.request.TokenRefreshRequest;
import org.toastit_v2.common.jwt.web.response.TokenResponse;

@Tag(
        name = "Token",
        description = "토큰 재발급 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {

    private final TokenRefreshService tokenRefreshService;

    @Operation(
            summary = "액세스 & 리프레시 토큰 재발급",
            description = "저장된 리프레시 토큰으로 액세스 토큰을 재발급합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.FILED_ERROR,

    })
    @PatchMapping("/refresh")
    public TokenResponse refreshToken(@RequestBody @Valid TokenRefreshRequest request) {
        return TokenResponse.from(tokenRefreshService.refreshAccessToken(request));
    }

}
