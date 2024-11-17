package org.toastit_v2.feature.test.application.service;

import lombok.RequiredArgsConstructor;
import org.toastit_v2.feature.test.domain.Test;
import org.toastit_v2.feature.test.domain.TestCreate;
import org.toastit_v2.feature.test.application.port.TestRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository repository;

    @Override
    public Test createTest(TestCreate create) {
        Test test = Test.from(create);
        return repository.save(test);
    }

}
