package org.toastit_v2.core.infrastructure.persistence.test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.toastit_v2.core.domain.test.Test;

@Entity
@Builder
@AllArgsConstructor
@Table(name = "tests")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String test;

    public static TestEntity from(Test test) {
        return TestEntity.builder()
                .test(test.getTest())
                .build();
    }

    public Test toModel() {
        return Test.builder()
                .id(this.id)
                .test(this.test)
                .build();
    }

}
