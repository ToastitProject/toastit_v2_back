package org.toastit_v2.feature.test.application.service;

import org.toastit_v2.feature.test.domain.Test;
import org.toastit_v2.feature.test.domain.TestCreate;

public interface TestService {

    Test createTest(TestCreate test);
}
