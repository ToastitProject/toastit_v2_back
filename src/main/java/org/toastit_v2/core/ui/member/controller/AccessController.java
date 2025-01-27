package org.toastit_v2.core.ui.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.toastit_v2.common.annotation.swagger.ApiExceptionResponse;
import org.toastit_v2.common.annotation.swagger.ApiRequestBody;
import org.toastit_v2.common.annotation.swagger.ApiSuccessResponse;
import org.toastit_v2.common.response.SuccessResponse;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.common.response.code.SuccessCode;
import org.toastit_v2.core.application.member.service.MemberService;
import org.toastit_v2.core.domain.auth.security.CustomUserDetails;
import org.toastit_v2.core.ui.member.payload.request.LoginRequest;
import org.toastit_v2.core.ui.member.payload.response.LoginResponse;

@Tag(
        name = "Login & Out",
        description = "로그인, 로그아웃"
)
@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class AccessController {

    private final MemberService memberService;

    @Operation(
            summary = "로그인 요청 보내기",
            description = "로그인 API"
    )
    @ApiSuccessResponse(SuccessCode.SUCCESS)
    @ApiExceptionResponse({
            ExceptionCode.NOT_EXISTS_USERID
    })
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<LoginResponse>> login(
            @ApiRequestBody(
                    description = "로그인 회원 정보",
                    content = @Content(
                            schema = @Schema(
                                    implementation = LoginRequest.class
                            )
                    )
            )
            @Valid @RequestBody final LoginRequest loginRequest
    ) {
        final String accessToken = memberService.login(loginRequest);
        final LoginResponse response = new LoginResponse(accessToken);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        response,
                        SuccessCode.SUCCESS.getHttpStatus(),
                        SuccessCode.SUCCESS.getMessage(),
                        SuccessCode.SUCCESS.getStatusCode()
                )
        );
    }

    @Operation(
            summary = "로그아웃 요청 보내기",
            description = "로그아웃 API"
    )
    @ApiSuccessResponse(SuccessCode.SUCCESS)
    @ApiExceptionResponse({
            ExceptionCode.NOT_EXISTS_USERID
    })
    @DeleteMapping("/logout")
    public ResponseEntity<Object> logout(@AuthenticationPrincipal final CustomUserDetails userDetails) {
        memberService.logout(userDetails.getUserId());
        return ResponseEntity.ok(
                new SuccessResponse<>(
                        null,
                        SuccessCode.SUCCESS.getHttpStatus(),
                        SuccessCode.SUCCESS.getMessage(),
                        SuccessCode.SUCCESS.getStatusCode()
                )
        );
    }

}
