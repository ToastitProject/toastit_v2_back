package org.toastit_v2.core.application.auth.mail.port;

@FunctionalInterface
public interface AuthMailSender {

    void send(String authNumber, String userEmail);

}
