package org.toastit_v2.core.application.auth.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.toastit_v2.common.exception.custom.CustomAuthMailException;
import org.toastit_v2.common.generator.auth.mail.MailKeyGeneratorImpl;
import org.toastit_v2.common.generator.date.DateTimeGeneratorImpl;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.application.auth.mail.port.AuthMailRepository;
import org.toastit_v2.core.application.auth.mail.port.AuthMailSender;
import org.toastit_v2.core.domain.auth.mail.AuthMail;
import org.toastit_v2.core.ui.auth.mail.payload.request.AuthMailRequest;

@Service
@RequiredArgsConstructor
public class AuthMailServiceImpl implements AuthMailService {

    private final AuthMailRepository authMailRepository;
    private final AuthMailSender authMailSender;

    public void send(final AuthMailRequest request) {
        final AuthMail authMail = AuthMail.create(
                request.userEmail(),
                new MailKeyGeneratorImpl(),
                new DateTimeGeneratorImpl()
        );
        authMailSender.send(authMail.getAuthNumber(), authMail.getUserEmail());
        authMailRepository.save(authMail);
    }

    public void validateAuthMail(final String userEmail, final String authNumber) {
        final AuthMail authMail = authMailRepository.findById(userEmail).orElseThrow(
                () -> new CustomAuthMailException(ExceptionCode.AUTH_EMAIL_EXPIRED_ERROR)
        );
        authMail.checkAuthNumber(authNumber);
    }

}
