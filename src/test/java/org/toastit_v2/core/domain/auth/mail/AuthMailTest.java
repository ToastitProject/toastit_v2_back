package org.toastit_v2.core.domain.auth.mail;

import org.junit.jupiter.api.Test;
import org.toastit_v2.common.exception.custom.CustomAuthMailException;
import org.toastit_v2.common.response.code.ExceptionCode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.toastit_v2.common.fixture.auth.AuthMailFixture.*;

class AuthMailTest {

    @Test
    void 인증_메일을_생성한다() {
        // given
        // when
        final AuthMail response = AuthMail.create(
                DEFAULT_EMAIL,
                () -> DEFAULT_AUTH_CODE,
                () -> DEFAULT_CREATED_AT
        );

        // then
        assertThat(response.getUserEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(response.getAuthCode()).isEqualTo(DEFAULT_AUTH_CODE);
        assertThat(response.getRegisterDate()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    void 인증_번호가_일치하면_예외가_발생하지_않고_검증에_성공한다() {
        // given
        final AuthMail response = authMail();

        // when & then
        response.checkAuthNumber(DEFAULT_AUTH_CODE); // 예외가 발생하지 않으면 성공
    }

    @Test
    void 인증_번호가_일치하지_않으면_예외가_발생한다() {
        // given
        final AuthMail response = authMail();

        // when & then
        assertThatThrownBy(() -> response.checkAuthNumber("654321"))
                .isInstanceOf(CustomAuthMailException.class)
                .hasMessageContaining(ExceptionCode.AUTH_EMAIL_AUTH_NUMBER_ERROR.getMessage());
    }

}
