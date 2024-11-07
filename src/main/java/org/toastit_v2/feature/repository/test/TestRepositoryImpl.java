package org.toastit_v2.feature.repository.test;

import lombok.RequiredArgsConstructor;
import org.toastit_v2.feature.domain.test.Test;
import org.toastit_v2.feature.model.entity.test.TestEntity;
import org.toastit_v2.feature.service.test.port.TestRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TestRepositoryImpl implements TestRepository {

    private final JpaTestRepository repository;

    @Transactional
    @Override
    public Test save(Test domain) {
        return repository.save(TestEntity.fromModel(domain)).toModel();
    }

}
