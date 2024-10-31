package org.example.toastit_v2.feature.service.test.impl;

import org.example.toastit_v2.feature.domain.test.Test;
import org.example.toastit_v2.feature.domain.test.TestCreate;
import org.example.toastit_v2.feature.service.test.TestService;
import org.example.toastit_v2.feature.service.test.port.TestRepository;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public Test createTest(TestCreate create) {
        Test test = Test.from(create);
        return testRepository.save(test);
    }
}