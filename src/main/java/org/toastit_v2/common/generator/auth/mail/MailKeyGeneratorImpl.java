package org.toastit_v2.common.generator.auth.mail;

import java.util.Random;

public class MailKeyGeneratorImpl implements MailKeyGenerator {

    @Override
    public String generate() {
        StringBuilder key = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < 8; i++) {
            int index = rand.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0 -> key.append((char) (rand.nextInt(26) + 97));
                case 1 -> key.append((char) (rand.nextInt(26) + 65));
                case 2 -> key.append((rand.nextInt(10)));
            }
        }

        return key.toString().toLowerCase();
    }

}
