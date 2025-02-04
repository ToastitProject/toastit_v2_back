package org.toastit_v2.core.domain.auth.token;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.toastit_v2.common.fixture.auth.TokenFixture.*;

class TokenTest {

    @Test
    void 토큰을_생성한다() {
        // given
        // when
        final Token response = Token.create(DEFAULT_USER_ID, DEFAULT_TOKEN);

        // then
        assertThat(response.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(response.getAccessToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @Test
    void 동일한_토큰을_비교한다() {
        // given
        // when
        final Token token1 = token();
        final Token token2 = token();

        // then
        assertThat(token1).isEqualTo(token2);
    }

    @Test
    void 서로_다른_토큰을_비교하면_동등하지_않은_결과를_반환한다() {
        // given
        final Token token1 = token();
        final Token token2 = token("rowing190328", DEFAULT_TOKEN);

        // when & then
        assertThat(token1).isNotEqualTo(token2);
    }

}
