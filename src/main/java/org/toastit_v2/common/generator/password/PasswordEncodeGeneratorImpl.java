package org.toastit_v2.common.generator.password;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class PasswordEncodeGeneratorImpl implements PasswordEncodeGenerator {

    private final PasswordEncoder encoder;

    @Override
    public String generate(String password) {
        return encoder.encode(password);
    }

}
