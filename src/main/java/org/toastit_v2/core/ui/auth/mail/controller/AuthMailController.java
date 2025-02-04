package org.toastit_v2.core.ui.auth.mail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.toastit_v2.common.annotation.swagger.ApiExceptionResponse;
import org.toastit_v2.common.annotation.swagger.ApiRequestBody;
import org.toastit_v2.common.annotation.swagger.ApiSuccessResponse;
import org.toastit_v2.common.response.SuccessResponse;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.common.response.code.SuccessCode;
import org.toastit_v2.core.application.auth.mail.service.AuthMailService;
import org.toastit_v2.core.ui.auth.mail.payload.request.AuthMailRequest;

@Tag(
        name = "Auth Mail",
        description = "인증 메일 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/auth")
public class AuthMailController {

    private final AuthMailService authMailService;

    @Operation(
            summary = "인증 메일 보내기",
            description = "인증 메일 보내기 API"
    )
    @ApiSuccessResponse(SuccessCode.SUCCESS)
    @ApiExceptionResponse({
            ExceptionCode.AUTH_EMAIL_REQUEST_ERROR,
            ExceptionCode.AUTH_EMAIL_DUPLICATION_ERROR
    })
    @PostMapping("/mail")
    public ResponseEntity<SuccessResponse<Object>> sendAuthMail(
            @ApiRequestBody(
                    description = "이메일 인증 보내기 요청 정보",
                    content = @Content(
                            schema = @Schema(implementation = AuthMailRequest.class)
                    )
            )
            @Valid @RequestBody final AuthMailRequest request
    ) {
        authMailService.send(request);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        null,
                        SuccessCode.SUCCESS.getHttpStatus(),
                        SuccessCode.SUCCESS.getMessage(),
                        SuccessCode.SUCCESS.getStatusCode()
                )
        );
    }

    @Operation(
            summary = "이메일 인증 번호 확인",
            description = "이메일 인증 번호 확인 성공 API"
    )
    @ApiSuccessResponse(SuccessCode.SUCCESS)
    @ApiExceptionResponse({
            ExceptionCode.AUTH_EMAIL_EXPIRED_ERROR
    })
    @GetMapping("/email/{auth_number}")
    public ResponseEntity<SuccessResponse<Object>> checkAuthMail(
            @Parameter(
                    description = "회원 이메일",
                    example = "test@naver.com"
            )
            @RequestParam("user_email") final String userEmail,
            @Parameter(
                    description = "인증 번호",
                    example = "asdf15130"
            )
            @PathVariable("auth_number") final String authNumber
    ) {
        authMailService.validateAuthMail(userEmail, authNumber);

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
