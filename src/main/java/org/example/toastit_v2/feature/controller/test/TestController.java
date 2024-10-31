package org.example.toastit_v2.feature.controller.test;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.example.toastit_v2.feature.controller.test.response.TestResponse;
import org.example.toastit_v2.feature.domain.test.TestCreate;
import org.example.toastit_v2.feature.service.test.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<TestResponse> create(@RequestBody @Valid TestCreate create) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TestResponse.from(testService.createTest(create)));
    }
}
