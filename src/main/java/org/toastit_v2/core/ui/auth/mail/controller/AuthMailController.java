package org.toastit_v2.core.ui.auth.mail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
            @Valid @RequestBody AuthMailRequest request
    ) {
        authMailService.send(request);

        return ResponseEntity.ok(
                new SuccessResponse<>(HttpStatus.OK, null, "메일 전송 성공", 200)
        );
    }

}
