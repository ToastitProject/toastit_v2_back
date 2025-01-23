package org.toastit_v2.core.application.test.service;

import org.toastit_v2.core.domain.test.Test;
import org.toastit_v2.core.domain.test.TestCreate;

public interface TestService {

    Test createTest(TestCreate test);

}
