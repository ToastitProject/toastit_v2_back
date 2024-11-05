package org.toastit_v2.feature.domain.test;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestCreate {

    @NotNull
    String test;
}
