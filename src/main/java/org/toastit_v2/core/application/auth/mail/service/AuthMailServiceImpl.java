package org.toastit_v2.core.application.auth.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.toastit_v2.common.exception.custom.CustomAuthMailException;
import org.toastit_v2.common.generator.auth.mail.MailAuthCodeGeneratorImpl;
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

    @Override
    public void send(final AuthMailRequest request) {
        final AuthMail authMail = AuthMail.create(
                request.userEmail(),
                new MailAuthCodeGeneratorImpl(),
                new DateTimeGeneratorImpl()
        );
        authMailSender.send(authMail.getAuthCode(), authMail.getUserEmail());
        authMailRepository.save(authMail);
    }

    @Override
    public void validateAuthMail(final String userEmail, final String authNumber) {
        final AuthMail authMail = authMailRepository.findById(userEmail).orElseThrow(
                () -> new CustomAuthMailException(ExceptionCode.AUTH_MAIL_EXPIRED_ERROR)
        );
        authMail.checkAuthNumber(authNumber);
    }

}
