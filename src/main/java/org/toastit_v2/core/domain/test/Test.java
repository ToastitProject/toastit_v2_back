package org.toastit_v2.core.domain.test;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Test {

    private final Long id;
    private final String test;

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
