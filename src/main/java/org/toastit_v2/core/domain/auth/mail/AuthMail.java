package org.toastit_v2.core.domain.auth.mail;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.toastit_v2.common.exception.custom.CustomAuthMailException;
import org.toastit_v2.common.generator.auth.mail.MailAuthCodeGenerator;
import org.toastit_v2.common.generator.date.DateTimeGenerator;
import org.toastit_v2.common.response.code.ExceptionCode;

import java.time.LocalDateTime;

@RedisHash(
        value = "authMail",
        timeToLive = 6 * 60
)
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthMail {

    @Id
    private String userEmail;

    private String authCode;

    private LocalDateTime registerDate;

    @Builder
    private AuthMail(final String userEmail, final String authCode, final LocalDateTime registerDate) {
        this.userEmail = userEmail;
        this.authCode = authCode;
        this.registerDate = registerDate;
    }

    public static AuthMail create(final String userEmail, MailAuthCodeGenerator mailAuthCodeGenerator, DateTimeGenerator dateTimeGenerator) {
        return new AuthMail(
                userEmail,
                mailAuthCodeGenerator.generate(),
                dateTimeGenerator.generate()
        );
    }

    public void checkAuthNumber(final String authNumber) {
        if (!authNumber.equals(this.authCode)) {
            throw new CustomAuthMailException(ExceptionCode.AUTH_EMAIL_AUTH_NUMBER_ERROR);
        }
    }

}
