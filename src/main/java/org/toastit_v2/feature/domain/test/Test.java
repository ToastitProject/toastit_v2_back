package org.toastit_v2.feature.domain.test;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Test {

    private Long id;

    private String test;

    @Builder
    public Test(Long id, String test) {
        this.id = id;
        this.test = test;
    }

    public static Test from(TestCreate create) {
        return Test.builder()
                .test(create.getTest())
                .build();
    }

}
