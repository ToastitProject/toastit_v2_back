package org.toastit_v2.feature.test.web.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class TestCreateRequest {

    @NotNull
    @Schema(description = "이메일", example = "example@gmail.com")
    private final String test;

    @JsonCreator
    public TestCreateRequest(String test) {
        this.test = test;
    }

}
