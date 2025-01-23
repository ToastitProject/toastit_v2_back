package org.toastit_v2.feature.email.application.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.toastit_v2.common.common.application.code.CommonExceptionCode;
import org.toastit_v2.common.common.application.exception.RestApiException;
import org.toastit_v2.feature.email.application.util.RandomAuthCode;
import org.toastit_v2.feature.user.application.port.UserRepository;
import org.toastit_v2.feature.email.application.port.MailSender;
import org.toastit_v2.feature.email.web.request.EmailAuthRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailAuthServiceImpl implements EmailAuthService {

    private static final String TEMPLATE_PATH = "/templates/auth/mail-form.html";

    private final EmailCodeService emailCodeService;

    private final UserRepository userRepository;

    private final RandomAuthCode randomAuthCode;
    private final MailSender mailSender;

    @Override
    public void sendAuthMail(EmailAuthRequest request) {
        String code = randomAuthCode.generate();
        emailCodeService.save(request.getEmail(), code);
        String subject = "[ToastIT] 이메일 인증번호 : " + code;
        String body = generateAuthMailBody(code);
        mailSender.send(request.getEmail(), subject, body);
    }

    @Override
    public void checkDuplicate(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new RestApiException(CommonExceptionCode.EXIST_EMAIL_ERROR);
        });
    }

    @Override
    public void verifyEmailWithCode(String email, String code) {
        String storedCode = emailCodeService.findByEmail(email);
        if (!code.equals(storedCode)) {
            throw new RestApiException(CommonExceptionCode.NOT_MATCH_AUTH_CODE);
        }
    }

    private String generateAuthMailBody(String code) {
        try (InputStream inputStream = getClass().getResourceAsStream(TEMPLATE_PATH)) {
            if (inputStream == null) {
                log.error("Template not found: {}", TEMPLATE_PATH);
                throw new RestApiException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
            }

            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            content = content.replace("{{authcode}}", code);
            return content;

        } catch (IOException e) {
            log.error("Exception : {}", e.getMessage(), e);
            throw new RestApiException(CommonExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }

}
