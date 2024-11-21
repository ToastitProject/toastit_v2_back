package org.toastit_v2.feature.email.application.port;

public interface MailSender {

    void send(String to, String subject, String body);

}
