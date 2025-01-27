package org.toastit_v2.common.generator.nickname;

import java.util.Random;

import static org.toastit_v2.common.generator.nickname.Nickname.names;
import static org.toastit_v2.common.generator.nickname.Nickname.prefix;

public class NicknameGeneratorImpl implements NicknameGenerator {

    private final Random random = new Random();

    @Override
    public String generate() {
        return prefix[random.nextInt(prefix.length)]+" "+names[(random.nextInt(names.length))];
    }

}
