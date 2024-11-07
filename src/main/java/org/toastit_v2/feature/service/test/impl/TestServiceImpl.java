package org.toastit_v2.feature.service.test.impl;

import lombok.RequiredArgsConstructor;
import org.toastit_v2.feature.domain.test.Test;
import org.toastit_v2.feature.domain.test.TestCreate;
import org.toastit_v2.feature.service.test.TestService;
import org.toastit_v2.feature.service.test.port.TestRepository;
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
