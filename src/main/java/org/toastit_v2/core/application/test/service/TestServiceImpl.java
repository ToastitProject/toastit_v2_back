package org.toastit_v2.core.application.test.service;

import lombok.RequiredArgsConstructor;
import org.toastit_v2.core.domain.test.Test;
import org.toastit_v2.core.domain.test.TestCreate;
import org.toastit_v2.core.application.test.port.TestRepository;
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
