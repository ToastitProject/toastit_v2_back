package org.example.toastit_v2.feature.model.entity.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.example.toastit_v2.feature.domain.test.Test;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tests")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String test;

    public static TestEntity fromModel(Test test) {
        return TestEntity.builder()
                .test(test.getTest())
                .build();
    }

    public Test toModel() {
        return Test.builder()
                .id(id)
                .test(test)
                .build();
    }
}