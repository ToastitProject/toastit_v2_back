package org.toastit_v2.feature.test.infrastructure.persistence.mysql;

import org.springframework.stereotype.Repository;
import org.toastit_v2.core.common.infrastructure.persistence.JpaBaseRepository;

@Repository
public interface JpaTestRepository extends JpaBaseRepository<TestEntity, Long> {
}
