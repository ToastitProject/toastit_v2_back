package org.toastit_v2.feature.service.test;

import org.toastit_v2.feature.domain.test.Test;
import org.toastit_v2.feature.domain.test.TestCreate;

public interface TestService {

    Test createTest(TestCreate test);
}
