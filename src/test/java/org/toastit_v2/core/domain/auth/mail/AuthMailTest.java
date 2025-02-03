package org.toastit_v2.core.domain.auth.mail;

import org.junit.jupiter.api.Test;
import org.toastit_v2.common.exception.custom.CustomAuthMailException;
import org.toastit_v2.common.response.code.ExceptionCode;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthMailTest {

    @Test
    void 인증_메일을_생성한다() {
        // given
        final String email = "test@example.com";

        // when
        final AuthMail expected = AuthMail.create(
                email,
                () -> "123456",
                () -> LocalDateTime.of(2025, 1, 2, 12, 8, 0)
        );

        // then
        assertThat(expected.getUserEmail()).isEqualTo(email);
        assertThat(expected.getAuthCode()).isEqualTo("123456");
        assertThat(expected.getRegisterDate()).isEqualTo(LocalDateTime.of(2025, 1, 2, 12, 8, 0));
    }

    @Test
    void 인증_번호가_일치하면_예외가_발생하지_않고_검증에_성공한다() {
        // given
        final AuthMail expected = AuthMail.builder()
                .userEmail("test@example.com")
                .authCode("123456")
                .registerDate(LocalDateTime.of(2025, 1, 2, 12, 8, 0))
                .build();

        // when & then
        expected.checkAuthNumber("123456"); // 예외가 발생하지 않으면 성공
    }

    @Test
    void 인증_번호가_일치하지_않으면_예외가_발생한다() {
        // given
        final AuthMail expected = AuthMail.builder()
                .userEmail("test@example.com")
                .authCode("123456")
                .registerDate(LocalDateTime.of(2025, 1, 2, 12, 8, 0))
                .build();

        // when & then
        assertThatThrownBy(() -> expected.checkAuthNumber("654321"))
                .isInstanceOf(CustomAuthMailException.class)
                .hasMessageContaining(ExceptionCode.AUTH_EMAIL_AUTH_NUMBER_ERROR.getMessage());
    }

}
