package org.toastit_v2.core.infrastructure.persistence.test;

import org.springframework.stereotype.Repository;
import org.toastit_v2.common.common.infrastructure.persistence.JpaBaseRepository;

@Repository
public interface TestJpaRepository extends JpaBaseRepository<TestEntity, Long> {
}
