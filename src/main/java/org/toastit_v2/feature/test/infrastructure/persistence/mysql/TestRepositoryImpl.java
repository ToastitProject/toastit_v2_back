package org.toastit_v2.feature.test.infrastructure.persistence.mysql;

import lombok.RequiredArgsConstructor;
import org.toastit_v2.feature.test.domain.Test;
import org.toastit_v2.feature.test.application.port.TestRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TestRepositoryImpl implements TestRepository {

    private final JpaTestRepository repository;

    @Transactional
    @Override
    public Test save(Test domain) {
        return repository.save(TestEntity.from(domain)).toModel();
    }

}
