package org.toastit_v2.feature.email.infrastructure.mail;

import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.toastit_v2.core.common.application.code.CommonExceptionCode;
import org.toastit_v2.core.common.application.exception.RestApiException;
import org.toastit_v2.feature.email.application.port.MailSender;

@Slf4j
@Component
@Validated
public class MailSenderImpl implements MailSender {

    private final String fromEmail;

    private final JavaMailSender mailSender;

    public MailSenderImpl(
            @NotNull @Value("${spring.mail.username}") String fromEmail,
            JavaMailSender mailSender
    ) {
        this.fromEmail = fromEmail;
        this.mailSender = mailSender;
    }

    @Override
    public void send(String to, String subject, String body) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setSubject(subject);
            messageHelper.setTo(to);
            messageHelper.setFrom(fromEmail, "TOASTIT");
            messageHelper.setText(body, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new RestApiException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

}
