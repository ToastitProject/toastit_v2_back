package org.toastit_v2.feature.test.infrastructure.persistence.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.toastit_v2.feature.test.infrastructure.persistence.mysql.entity.TestEntity;

public interface JpaTestRepository extends JpaRepository<TestEntity, Long> {
}
