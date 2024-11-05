package org.toastit_v2.feature.controller.test;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.toastit_v2.feature.domain.test.TestCreate;
import org.toastit_v2.feature.service.test.TestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping
    @Operation(
            summary = "테스트 코드",
            description = "테스트 코드 생성"
    )
    public String create(@RequestBody @Valid TestCreate create) {

        testService.createTest(create);

        return "success";
    }
}
