package org.toastit_v2.core.domain.test;

import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.core.ui.test.payload.request.TestCreateRequest;

@Getter
@Builder
public class TestCreate {

    final String test;

    public static TestCreate from(TestCreateRequest request) {
        return TestCreate.builder()
                .test(request.getTest())
                .build();
    }

}
