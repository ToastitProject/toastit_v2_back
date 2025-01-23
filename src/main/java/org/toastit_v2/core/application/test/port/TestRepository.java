package org.toastit_v2.core.application.test.port;

import org.toastit_v2.core.domain.test.Test;

public interface TestRepository {

    Test save(Test test);

}
