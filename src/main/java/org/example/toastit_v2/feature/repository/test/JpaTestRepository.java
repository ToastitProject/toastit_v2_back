package org.example.toastit_v2.feature.repository.test;

import org.example.toastit_v2.feature.model.entity.test.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTestRepository extends JpaRepository<TestEntity, Long> {
}
