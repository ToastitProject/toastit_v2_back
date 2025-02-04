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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.toastit_v2.common.fixture.AuthMailFixture.*;

@SpringBootTest
@ActiveProfiles("test")
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
        // when
        authMailService.send(new AuthMailRequest(DEFAULT_EMAIL));

        // then
        final Optional<AuthMail> excepted = authMailRepository.findById(DEFAULT_EMAIL);
        assertThat(excepted).isPresent();
        assertThat(excepted.get().getUserEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(excepted.get().getAuthCode()).isNotNull();
    }

    @Test
    void 인증_번호를_검증하면_예외가_발생하지_않고_검증에_성공한다() {
        // given
        final AuthMail authMail = AuthMail.create(DEFAULT_EMAIL, () -> DEFAULT_AUTH_CODE, () -> DEFAULT_CREATED_AT);
        authMailRepository.save(authMail);

        // when & then
        authMailService.validateAuthMail(DEFAULT_EMAIL, DEFAULT_AUTH_CODE);
    }

    @Test
    void 잘못된_인증번호로_검증하면_예외가_발생한다() {
        // given
        final AuthMail authMail = AuthMail.create(DEFAULT_EMAIL, () -> DEFAULT_AUTH_CODE, () -> DEFAULT_CREATED_AT);
        authMailRepository.save(authMail);

        // when & then
        assertThatThrownBy(() -> authMailService.validateAuthMail(DEFAULT_EMAIL, "654321"))
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
        final AuthMailSender mockSender = mock(AuthMailSender.class);
        final AuthMailService serviceWithMockSender = new AuthMailServiceImpl(authMailRepository, mockSender);

        // when
        serviceWithMockSender.send(new AuthMailRequest(DEFAULT_EMAIL));

        // then
        verify(mockSender, times(1)).send(anyString(), eq(DEFAULT_EMAIL));
    }

}
