package org.toastit_v2.core.infrastructure.auth.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.toastit_v2.common.exception.custom.CustomAuthMailException;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.application.auth.mail.port.AuthMailSender;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Validated
@Component
public class SmtpAuthMailHandler implements AuthMailSender {

    private static final String AUTH_MAIL_TEMPLATE_PATH = "/templates/auth-mail-form.html";

    private static final String AUTH_MAIL_SUBJECT = "회원님의 일회성 인증 키는 %s입니다.";

    private final String from;

    private final JavaMailSender javaMailSender;

    public SmtpAuthMailHandler(
            @NotNull @Value("${spring.mail.username}") final String from,
            final JavaMailSender javaMailSender
    ) {
        this.from = from;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void send(final String authNumber, final String userEmail) {
        javaMailSender.send(
                this.createMessage(authNumber, userEmail)
        );
    }

    private MimeMessage createMessage(final String authNumber, final String userEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.setSubject(String.format(AUTH_MAIL_SUBJECT, authNumber));
            mimeMessage.addRecipients(Message.RecipientType.TO, userEmail);
            mimeMessage.setText(createText(authNumber), "utf-8", "html");
            mimeMessage.setFrom(new InternetAddress(from, "Mixie"));
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("이메일 전송 실패 :: {}", e.getMessage());
            throw new CustomAuthMailException(ExceptionCode.AUTH_EMAIL_REQUEST_ERROR);
        }

        return mimeMessage;
    }

    private String createText(final String authNumber) {
        try (InputStream inputStream = getClass().getResourceAsStream(AUTH_MAIL_TEMPLATE_PATH)) {
            if (Objects.isNull(inputStream)) {
                log.error("템플릿을 찾을 수 없습니다 :: {}", AUTH_MAIL_TEMPLATE_PATH);
                throw new CustomAuthMailException(ExceptionCode.NOT_FOUND_ERROR);
            }
            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            content = content.replace("{authcode}", authNumber);
            return content;
        } catch (IOException e) {
            log.error("입출력 예외 발생 :: {}", e.getMessage());
            throw new CustomAuthMailException(ExceptionCode.INTERVAL_SERVER_ERROR);
        }
    }

}
