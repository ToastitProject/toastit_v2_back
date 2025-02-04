package org.toastit_v2.common.generator.date;

import java.time.LocalDateTime;

public class DateTimeGeneratorImpl implements DateTimeGenerator {

    @Override
    public LocalDateTime generate() {
        return LocalDateTime.now();
    }

}
