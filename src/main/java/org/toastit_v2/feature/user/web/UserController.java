package org.toastit_v2.feature.user.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.toastit_v2.common.common.application.code.CommonExceptionCode;
import org.toastit_v2.common.common.application.code.CommonResponseCode;
import org.toastit_v2.common.common.application.config.swagger.ExceptionCodeAnnotations;
import org.toastit_v2.common.common.application.config.swagger.ResponseCodeAnnotation;
import org.toastit_v2.common.security.domain.CustomUserDetails;
import org.toastit_v2.feature.user.application.service.UserAuthService;
import org.toastit_v2.feature.user.application.service.UserInfoService;
import org.toastit_v2.feature.user.web.request.UserJoinRequest;
import org.toastit_v2.feature.user.application.service.UserService;
import org.toastit_v2.feature.user.web.request.UserLoginRequest;
import org.toastit_v2.feature.user.web.response.UserLoginResponse;
import org.toastit_v2.feature.user.web.response.UserResponse;

@Tag(
        name = "User",
        description = "회원 기능 API"
)
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserInfoService infoService;
    private final UserAuthService authService;

    @Operation(
            summary = "로그인 유저 정보",
            description = "현재 로그인 되어있는 유저의 정보를 반환합니다."
    )
    @ExceptionCodeAnnotations({CommonExceptionCode.NOT_FOUND_USER})
    @GetMapping
    public UserResponse getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return UserResponse.from(infoService.getUserByDetails(userDetails));
    }

    @Operation(
            summary = "이메일 회원가입",
            description = "회원 데이터를 추가합니다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.CREATED)
    @ExceptionCodeAnnotations({CommonExceptionCode.FILED_ERROR})
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody @Valid UserJoinRequest request) {
        userService.save(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("회원가입이 완료되었습니다.");
    }

    @Operation(
            summary = "이메일과 로그인",
            description = "이메일과 비밀번호를 입력받아 JWT 토큰을 발급합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.FILED_ERROR,
            CommonExceptionCode.NOT_MATCH_PASSWORD
    })
    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody @Valid UserLoginRequest request) {
        return UserLoginResponse.from(authService.login(request));
    }

    @Operation(
            summary = "로그아웃",
            description = "저장된 JWT 토큰을 제거합니다."
    )
    @DeleteMapping("/logout")
    @ExceptionCodeAnnotations({CommonExceptionCode.NOT_FOUND_USER})
    public String logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
        authService.logout(userDetails);
        return "로그아웃 되었습니다.";
    }

}
