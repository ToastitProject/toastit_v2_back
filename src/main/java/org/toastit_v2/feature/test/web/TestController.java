package org.toastit_v2.feature.test.web;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.toastit_v2.feature.test.domain.TestCreate;
import org.toastit_v2.feature.test.application.service.TestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.toastit_v2.feature.test.web.request.TestCreateRequest;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor

public class TestController {

    private final TestService testService;

    @PostMapping
    @Operation(
            summary = "테스트 코드",
            description = "테스트 코드 생성"
    )
    public String create(@RequestBody @Valid TestCreateRequest request) {

        testService.createTest(TestCreate.from(request));

        return "테스트 코드 실행 성공";
    }

}
