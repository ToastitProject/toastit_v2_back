package org.toastit_v2.core.domain.auth.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.toastit_v2.core.domain.member.Member;

import static org.assertj.core.api.Assertions.assertThat;

class CustomUserDetailsTest {

    private Member member;

    @BeforeEach
    void setUp() {
        member = Mockito.mock(Member.class);
        Mockito.when(member.getUserId()).thenReturn("rowing0328");
        Mockito.when(member.getAuthority()).thenReturn(Authority.USER);
    }

    @Test
    void 사용자_세부_정보를_생성한다() {
        // given & when
        CustomUserDetails expected = CustomUserDetails.from(member);

        // then
        assertThat(expected.getUserId()).isEqualTo("rowing0328");
        assertThat(expected.getAuthorities())
                .extracting(GrantedAuthority::getAuthority)
                .containsExactly("ROLE_USER");
    }

}
