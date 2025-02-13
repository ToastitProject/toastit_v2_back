package org.toastit_v2.core.infrastructure.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.toastit_v2.common.exception.custom.CustomJwtException;
import org.toastit_v2.common.generator.auth.jwt.JwtGenerator;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.domain.auth.token.TokenStatus;
import org.toastit_v2.core.domain.member.Member;

import javax.crypto.SecretKey;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.toastit_v2.common.fixture.auth.TokenFixture.*;

class JwtHandlerTest {

    private JwtHandler jwtHandler;
    private JwtGenerator jwtGenerator;
    private SecretKey key;
    private Member member;

    @BeforeEach
    void setUp() {
        jwtHandler = new JwtHandler();
        jwtGenerator = new JwtGenerator();
        key = Keys.hmacShaKeyFor(DEFAULT_TOKEN.getBytes());
        member = Mockito.mock(Member.class);
        Mockito.when(member.getUserId()).thenReturn(DEFAULT_USER_ID);
    }

    @Test
    void 유효한_토큰_상태를_확인하면_AUTHENTICATED_상태가_반환된다() {
        // given
        final String request = jwtGenerator.generate(key, 60000, member);

        // when
        final TokenStatus response = jwtHandler.getTokenStatus(request, key);

        // then
        assertThat(response).isEqualTo(TokenStatus.AUTHENTICATED);
    }

    @Test
    void 만료된_토큰_상태를_확인하면_EXPIRED_상태가_반환된다() {
        // given
        final String request = jwtGenerator.generate(key, 0, member);

        // when
        final TokenStatus response = jwtHandler.getTokenStatus(request, key);

        // then
        assertThat(response).isEqualTo(TokenStatus.EXPIRED);
    }

    @Test
    void 유효하지_않은_토큰을_검증하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> jwtHandler.getTokenStatus(INVALID_TOKEN, key))
                .isInstanceOf(CustomJwtException.class)
                .hasMessageContaining(ExceptionCode.JWT_UNKNOWN_ERROR.getMessage());
    }

    @Test
    void 유효한_토큰을_파싱한다() {
        // given
        final String request = jwtGenerator.generate(key, 60000, member);

        // when
        final Claims response = jwtHandler.parseToken(request, key);

        // then
        assertThat(response.getSubject()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    void 유효하지_않은_토큰을_파싱하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> jwtHandler.parseToken(INVALID_TOKEN, key))
                .isInstanceOf(CustomJwtException.class)
                .hasMessageContaining(ExceptionCode.JWT_INVALID_ERROR.getMessage());
    }

}
