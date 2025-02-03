package org.toastit_v2.core.application.auth.security.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.toastit_v2.common.exception.custom.CustomMemberException;
import org.toastit_v2.common.response.code.ExceptionCode;
import org.toastit_v2.core.domain.auth.security.CustomUserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SqlGroup({
    @Sql(value = "/sql/member/insert-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    @Sql(value = "/sql/member/delete-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void 유효한_회원으로_인증_요청을_하면_사용자_정보가_반환된다() {
        // when
        CustomUserDetails excepted = (CustomUserDetails) userDetailsService.loadUserByUsername("rowing0328");

        // then
        assertThat(excepted).isNotNull();
        assertThat(excepted.getUsername()).isEqualTo("rowing0328");
        assertThat(excepted.getAuthorities())
                .extracting(GrantedAuthority::getAuthority)
                .containsExactly("ROLE_USER");
    }

    @Test
    void 존재하지_않는_회원을_조회하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("nonexistentUser"))
                .isInstanceOf(CustomMemberException.class)
                .hasMessageContaining(ExceptionCode.MEMBER_NOT_FOUND.getMessage());
    }

}
