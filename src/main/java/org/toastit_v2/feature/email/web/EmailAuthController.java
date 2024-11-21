package org.toastit_v2.feature.email.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.config.swagger.ExceptionCodeAnnotations;
import org.toastit_v2.feature.email.application.service.EmailAuthService;
import org.toastit_v2.feature.email.web.request.EmailAuthRequest;

@Tag(
        name = "Email Authentication",
        description = "이메일 인증 관련 API"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailAuthController {

    private final EmailAuthService authService;

    @Operation(
            summary = "이메일 중복 체크",
            description = "회원가입 시 중복된 이메일인지 확인합니다."
    )
    @ExceptionCodeAnnotations({CommonExceptionCode.FILED_ERROR})
    @GetMapping("/duplicate")
    public String checkDuplicate(
            @Parameter(description = "이메일") @RequestParam @NotBlank String email
    ) {
        authService.checkDuplicate(email);
        return "사용 가능한 이메일입니다.";
    }

    @Operation(
            summary = "인증번호 검증",
            description = "입력받은 인증번호와 서버에 저장된 인증번호를 대조하여 이메일을 인증 처리 합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.FILED_ERROR,
            CommonExceptionCode.NOT_MATCH_AUTH_CODE
    })
    @GetMapping("/auth")
    public String verifyAuthCode(
            @Parameter(description = "이메일") @RequestParam @NotBlank @Email String email,
            @Parameter(description = "이메일 인증 코드") @RequestParam("auto_code") @NotBlank String authCode
            ) {
        authService.verifyEmailWithCode(email, authCode);
        return "인증이 완료되었습니다.";
    }

    @Operation(
            summary = "인증메일 발송",
            description = "회원가입 인증메일을 발송합니다."
    )
    @ExceptionCodeAnnotations(CommonExceptionCode.FILED_ERROR)
    @PostMapping("/auth")
    public String sendAuthMail(@RequestBody @Valid EmailAuthRequest request) {
        authService.sendAuthMail(request);
        return "인증 메일이 발송되었습니다.";
    }

}
