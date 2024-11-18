package org.toastit_v2.feature.user.web;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.toastit_v2.core.security.domain.CustomUserDetails;
import org.toastit_v2.feature.user.web.response.UserResponse;
import org.toastit_v2.feature.user.application.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "로그인 유저 정보",
            description = "현재 로그인 되어있는 유저의 정보를 반환합니다."
    )
    @GetMapping
    public UserResponse getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.getByUserDetails(userDetails);
    }

}
