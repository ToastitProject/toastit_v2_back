package org.example.toastit_v2.feature.service.test;

import org.example.toastit_v2.feature.domain.test.Test;
import org.example.toastit_v2.feature.domain.test.TestCreate;

public interface TestService {
    Test createTest(TestCreate test);
}