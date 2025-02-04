package org.toastit_v2.common.generator.password;

@FunctionalInterface
public interface PasswordEncodeGenerator {

    String generate(String password);

}
