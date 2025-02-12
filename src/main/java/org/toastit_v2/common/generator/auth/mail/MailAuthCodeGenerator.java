package org.toastit_v2.common.generator.auth.mail;

@FunctionalInterface
public interface MailAuthCodeGenerator {

    String generate();

}
