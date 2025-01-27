package org.toastit_v2.core.ui.member.controller;

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
import org.toastit_v2.core.application.member.user.service.MemberService;
import org.toastit_v2.core.ui.member.payload.request.SignUpRequest;

import java.net.URI;

@Tag(
        name = "SignUp",
        description = "회원가입"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class SignUpController {

    private final MemberService memberService;

    @Operation(
            summary = "회원가입 요청 보내기",
            description = "회원가입 API"
    )
    @ApiSuccessResponse(SuccessCode.CREATED)
    @ApiExceptionResponse({
            ExceptionCode.BAD_REQUEST_ERROR,
            ExceptionCode.PASSWORD_MISS_MATCH_ERROR,
            ExceptionCode.DUPLICATION_USERID_ERROR
    })
    @PostMapping("/member")
    public ResponseEntity<SuccessResponse<Object>> signUp(
            @ApiRequestBody(
                    description = "회원가입 요청 정보",
                    content = @Content(
                            schema = @Schema(
                                    implementation = SignUpRequest.class
                            )
                    )
            )
            @Valid @RequestBody final SignUpRequest request
    ) {
        String userId = memberService
                .signUp(request)
                .getUserId();

        return ResponseEntity.created(URI.create("/api/v2/member/" + userId))
                .body(new SuccessResponse<>(
                        null,
                        SuccessCode.CREATED.getHttpStatus(),
                        SuccessCode.CREATED.getMessage(),
                        SuccessCode.CREATED.getStatusCode()
                ));
    }

    @Operation(
            summary = "회원 아이디 중복 체크 요청",
            description = "회원 아이디 중복 체크 요청 API"
    )
    @ApiSuccessResponse(SuccessCode.SUCCESS)
    @GetMapping("/users/duplicate-check/{user_id}")
    public ResponseEntity<SuccessResponse<Object>> checkUserIdDuplicate(
            @Parameter(
                    description = "회원 아이디",
                    example = "mixietest"
            )
            @PathVariable("user_id") final String userId
    ) {
        memberService.checkUserIdDuplicate(userId);

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
