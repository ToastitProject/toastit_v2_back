package org.toastit_v2.feature.test.application.port;

import org.toastit_v2.feature.test.domain.Test;

public interface TestRepository {

    Test save(Test test);

}
