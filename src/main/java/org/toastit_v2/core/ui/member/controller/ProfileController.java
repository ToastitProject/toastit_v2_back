package org.toastit_v2.core.ui.member.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.toastit_v2.common.response.SuccessResponse;
import org.toastit_v2.common.response.code.SuccessCode;
import org.toastit_v2.core.application.member.user.service.MemberService;
import org.toastit_v2.core.domain.member.Member;
import org.toastit_v2.core.ui.member.payload.response.ProfileResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/member")
public class ProfileController {

    private final MemberService memberService;

    @GetMapping("/{user_id}")
    public ResponseEntity<SuccessResponse<ProfileResponse>> find(
            @Parameter(
                    description = "회원 아이디",
                    example = "mixie0328"
            )
            @PathVariable("user_id") final String userId
    ) {
        Member member = memberService.findByUserId(userId);
        ProfileResponse response = ProfileResponse.from(member);

        return ResponseEntity.ok(
                new SuccessResponse<>(
                        response,
                        SuccessCode.SUCCESS.getHttpStatus(),
                        SuccessCode.SUCCESS.getMessage(),
                        SuccessCode.SUCCESS.getStatusCode()
                )
        );
    }

}
