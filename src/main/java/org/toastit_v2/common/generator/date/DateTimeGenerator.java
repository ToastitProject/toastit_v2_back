package org.toastit_v2.common.generator.date;

import java.time.LocalDateTime;

@FunctionalInterface
public interface DateTimeGenerator {

    LocalDateTime generate();

}
