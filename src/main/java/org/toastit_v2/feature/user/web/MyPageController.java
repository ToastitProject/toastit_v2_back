package org.toastit_v2.feature.user.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.config.swagger.ExceptionCodeAnnotations;
import org.toastit_v2.core.security.domain.CustomUserDetails;
import org.toastit_v2.feature.user.application.service.UserInfoService;
import org.toastit_v2.feature.user.web.request.UserUpdateNicknameRequest;
import org.toastit_v2.feature.user.web.request.UserUpdatePasswordRequest;

@Tag(name = "MyPage", description = "마이페이지 기능 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final UserInfoService infoService;

    @Operation(
            summary = "닉네임 중복 체크",
            description = "회원 정보 수정 시 중복된 닉네임인지 확인합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.FILE_ERROR,
            CommonExceptionCode.EXIST_NICKNAME_ERROR
    })
    @GetMapping("/nickname/duplicate")
    public String checkDuplicate(
            @Parameter(
                    description = "사용할 닉네임"
            )
            @RequestParam("nickname") @NotBlank String nickname
    ) {
        infoService.checkDuplicate(nickname);
        return "사용 가능한 닉네임입니다.";
    }

    @Operation(
            summary = "닉네임 변경 기능",
            description = "회원 정보 수정 시 닉네임을 변경합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.FILE_ERROR,
            CommonExceptionCode.EXIST_NICKNAME_ERROR,
            CommonExceptionCode.NOT_FOUND_USER
    })
    @PatchMapping("/nickname")
    public String updateNickname(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid UserUpdateNicknameRequest request
    ) {
        infoService.update(userDetails, request);
        return "닉네임이 변경되었습니다.";
    }

    @Operation(
            summary = "비밀번호 변경 기능",
            description = "회원 정보 수정 시 비밀번호를 변경합니다."
    )
    @ExceptionCodeAnnotations({
            CommonExceptionCode.FILED_ERROR,
            CommonExceptionCode.NOT_FOUND_USER
    })
    @PatchMapping("/password")
    public String updatePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid UserUpdatePasswordRequest request
    ) {
        infoService.update(userDetails, request);
        return "비밀번호가 변경되었습니다.";
    }

//    @Operation(
//            summary = "프로필 이미지 변경 기능",
//            description = "유저 정보 수정 시 프로필 이미지를 변경합니다."
//    )
//    @ExceptionCodeAnnotations({
//            CommonExceptionCode.FILE_ERROR,
//            CommonExceptionCode.NOT_FOUND_USER
//    })
//    @PatchMapping(
//            value = "/profile/image",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//    )
//    public String changeImageUrl(
//            @AuthenticationPrincipal CustomUserDetails userDetails,
//            @RequestParam("file") MultipartFile file
//    ) {
//        infoService.update(userDetails, file);
//        return "프로필 이미지가 변경되었습니다.";
//    }

    @Operation(
            summary = "임시 회원 탈퇴",
            description = "사용자가 비활성화 상태로 변경됩니다."
    )
    @PatchMapping("/deactivate")
    @ExceptionCodeAnnotations({
            CommonExceptionCode.FILED_ERROR,
            CommonExceptionCode.NOT_FOUND_USER
    })
    public String deactivate(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request
    ) {
        infoService.deactivate(userDetails, request);
        return "정상적으로 탈퇴 되었습니다.";
    }

}
