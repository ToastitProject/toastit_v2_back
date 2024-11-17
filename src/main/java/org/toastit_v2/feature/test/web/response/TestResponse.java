package org.toastit_v2.feature.test.web.response;

import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.feature.test.domain.Test;

@Getter
public class TestResponse {

    private Long id;

    private String test;

    @Builder
    public TestResponse(Long id, String test) {
        this.id = id;
        this.test = test;
    }

    public static TestResponse from(Test test) {
        return TestResponse.builder()
                .id(test.getId())
                .test(test.getTest())
                .build();
    }

}
