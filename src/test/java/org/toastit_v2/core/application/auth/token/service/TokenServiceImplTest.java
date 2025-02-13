package org.toastit_v2.core.application.auth.token.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.toastit_v2.common.exception.custom.CustomJwtException;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.application.auth.token.port.TokenRepository;
import org.toastit_v2.core.application.member.port.MemberRepository;
import org.toastit_v2.core.domain.auth.security.Authority;
import org.toastit_v2.core.domain.member.Member;
import org.toastit_v2.core.domain.member.MemberProfile;
import org.toastit_v2.core.infrastructure.persistence.auth.token.TokenCrudRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.toastit_v2.common.fixture.auth.TokenFixture.*;

@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TokenServiceImplTest {

    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    private final TokenCrudRepository tokenCrudRepository;
    private final MemberRepository memberRepository;

    TokenServiceImplTest(
            final TokenService tokenService,
            final TokenRepository tokenRepository,
            final TokenCrudRepository tokenCrudRepository,
            final MemberRepository memberRepository
    ) {
        this.tokenService = tokenService;
        this.tokenRepository = tokenRepository;
        this.tokenCrudRepository = tokenCrudRepository;
        this.memberRepository = memberRepository;
    }

    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .userId("rowing0328")
                .password("@Mixie190328")
                .profile(MemberProfile.builder()
                        .userEmail("dev.hyoseung@gmail.com")
                        .content("안녕하세요. Mixie 테스트 입니다.")
                        .nickname("로잉0328")
                        .thumbnail("http://mixie.casa")
                        .build())
                .authority(Authority.USER)
                .registerDate(LocalDateTime.of(2025, 1, 15, 10, 47, 0))
                .modifyDate(LocalDateTime.of(2025, 1, 15, 10, 47, 0))
                .build();
        memberRepository.save(member);
    }

    @AfterEach
    void tearDown() {
        tokenCrudRepository.deleteAll();
        memberRepository.delete(member);
    }

    @Test
    void 회원_정보로_액세스_토큰을_생성한다() {
        // when
        tokenService.createAccessToken(member);

        // then
        assertThat(tokenRepository.findById(member.getUserId())).isPresent();
    }

    @Test
    void 요청에서_액세스_토큰을_추출한다() {
        // given
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", BEARER_TOKEN);

        // when
        final String response = tokenService.resolveAccessToken(request);

        // then
        assertThat(response).isEqualTo(DEFAULT_TOKEN);
    }

    @Test
    void 유효한_액세스_토큰을_검증한다() {
        // given
        final String request = tokenService.createAccessToken(member);

        // when
        boolean response = tokenService.validateAccessToken(request);

        // then
        assertThat(response).isTrue();
    }

    @Test
    void 잘못된_액세스_토큰을_검증하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> tokenService.validateAccessToken(INVALID_TOKEN))
                .isInstanceOf(CustomJwtException.class)
                .hasMessageContaining(ExceptionCode.JWT_UNKNOWN_ERROR.getMessage());
    }

    @Test
    void 액세스_토큰으로_인증을_생성한다() {
        // given
        final String request = tokenService.createAccessToken(member);

        // when
        final Authentication response = tokenService.getAuthenticationFrom(request);

        // then
        assertAll(
                () -> assertThat(response.getName()).isEqualTo(DEFAULT_USER_ID),
                () -> assertThat(response.getAuthorities())
                        .extracting(GrantedAuthority::getAuthority)
                        .containsExactly(Authority.USER.getAuthority())
        );
    }

    @Test
    void 로그아웃_한다() {
        // given
        tokenService.createAccessToken(member);

        // when
        tokenService.logout(member);

        // then
        assertThat(tokenRepository.findById(member.getUserId())).isEmpty();
    }

}
