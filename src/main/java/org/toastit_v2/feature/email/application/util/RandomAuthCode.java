package org.toastit_v2.feature.email.application.util;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class RandomAuthCode {

    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static final int CODE_LENGTH = 6;

    public static final SecureRandom RANDOM = new SecureRandom();

    public String generate() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int idx = RANDOM.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(idx));
        }
        return code.toString();
    }

}
