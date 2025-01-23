package org.toastit_v2.core.infrastructure.persistence.test;

import lombok.RequiredArgsConstructor;
import org.toastit_v2.core.domain.test.Test;
import org.toastit_v2.core.application.test.port.TestRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TestRepositoryImpl implements TestRepository {

    private final TestJpaRepository repository;

    @Transactional
    @Override
    public Test save(Test domain) {
        return repository.save(TestEntity.from(domain)).toModel();
    }

}
