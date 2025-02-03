package org.toastit_v2.core.application.auth.mail.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.toastit_v2.common.exception.custom.CustomAuthMailException;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.application.auth.mail.port.AuthMailRepository;
import org.toastit_v2.core.application.auth.mail.port.AuthMailSender;
import org.toastit_v2.core.domain.auth.mail.AuthMail;
import org.toastit_v2.core.infrastructure.persistence.auth.mail.AuthMailCrudRepository;
import org.toastit_v2.core.ui.auth.mail.payload.request.AuthMailRequest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthMailServiceImplTest {

    @Autowired
    private AuthMailService authMailService;

    @Autowired
    private AuthMailRepository authMailRepository;

    @Autowired
    private AuthMailCrudRepository authMailCrudRepository;

    @AfterEach
    void tearDown() {
        authMailCrudRepository.deleteAll();
    }

    @Test
    void 인증_메일을_전송하고_저장한다() {
        // given
        final AuthMailRequest request = new AuthMailRequest("dev.hyoseung@gmail.com");

        // when
        authMailService.send(request);

        // then
        final Optional<AuthMail> excepted = authMailRepository.findById("dev.hyoseung@gmail.com");
        assertThat(excepted).isPresent();
        assertThat(excepted.get().getUserEmail()).isEqualTo("dev.hyoseung@gmail.com");
        assertThat(excepted.get().getAuthCode()).isNotNull();
    }

    @Test
    void 인증_번호를_검증하면_예외가_발생하지_않고_검증에_성공한다() {
        // given
        final String userEmail = "test@example.com";
        final String authNumber = "123456";
        final AuthMail authMail = AuthMail.create(userEmail, () -> authNumber, () -> LocalDateTime.now());
        authMailRepository.save(authMail);

        // when & then
        authMailService.validateAuthMail(userEmail, authNumber);
    }

    @Test
    void 잘못된_인증번호로_검증하면_예외가_발생한다() {
        // given
        final String userEmail = "test@example.com";
        final String authNumber = "123456";
        final AuthMail authMail = AuthMail.create(userEmail, () -> authNumber, () -> LocalDateTime.now());
        authMailRepository.save(authMail);

        // when & then
        assertThatThrownBy(() -> authMailService.validateAuthMail(userEmail, "654321"))
                .isInstanceOf(CustomAuthMailException.class)
                .hasMessageContaining(ExceptionCode.AUTH_EMAIL_AUTH_NUMBER_ERROR.getMessage());
    }

    @Test
    void 만료된_인증메일로_검증하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> authMailService.validateAuthMail("nonexistent@example.com", "123456"))
                .isInstanceOf(CustomAuthMailException.class)
                .hasMessageContaining(ExceptionCode.AUTH_EMAIL_EXPIRED_ERROR.getMessage());
    }

    @Test
    void 인증_메일_전송_시_발송_기능을_호출한다() {
        // given
        final AuthMailRequest request = new AuthMailRequest("test@example.com");
        final AuthMailSender mockSender = mock(AuthMailSender.class);
        final AuthMailService serviceWithMockSender = new AuthMailServiceImpl(authMailRepository, mockSender);

        // when
        serviceWithMockSender.send(request);

        // then
        verify(mockSender, times(1)).send(anyString(), eq("test@example.com"));
    }

}
