package org.toastit_v2.feature.dto.test;

import lombok.Getter;
import org.toastit_v2.feature.domain.test.TestCreate;
import org.toastit_v2.feature.model.entity.test.TestEntity;

@Getter
public class TestDto {

    private  String test;

    public TestEntity toModel(TestCreate create) {
        return TestEntity.builder()
                .test(create.getTest())
                .build();
    }

}
