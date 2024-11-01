package org.toastit_v2.feature.service.test.port;

import org.toastit_v2.feature.domain.test.Test;

public interface TestRepository {
    Test save(Test test);
}