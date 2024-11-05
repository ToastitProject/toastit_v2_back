package org.toastit_v2.feature.repository.test;

import org.toastit_v2.feature.domain.test.Test;
import org.toastit_v2.feature.model.entity.test.TestEntity;
import org.toastit_v2.feature.service.test.port.TestRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TestRepositoryImpl implements TestRepository {

    private final JpaTestRepository jpaTestRepository;

    public TestRepositoryImpl(JpaTestRepository jpaTestRepository) {
        this.jpaTestRepository = jpaTestRepository;
    }

    @Transactional
    @Override
    public Test save(Test domain) {
        return jpaTestRepository.save(TestEntity.fromModel(domain)).toModel();
    }
}
