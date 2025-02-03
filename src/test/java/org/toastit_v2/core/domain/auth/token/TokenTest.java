package org.toastit_v2.core.domain.auth.token;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenTest {

    @Test
    void 토큰을_생성한다() {
        // given
        final String userId = "rowing0328";
        final String accessToken = "AYIDpalAMvEvQddKQXKoxO9OUc67N71r";

        // when
        Token expected = Token.create(userId, accessToken);

        // then
        assertThat(expected.getUserId()).isEqualTo(userId);
        assertThat(expected.getAccessToken()).isEqualTo(accessToken);
    }

    @Test
    void 동일한_토큰을_비교한다() {
        // given
        final Token token1 = Token.builder()
                .userId("rowing0328")
                .accessToken("AYIDpalAMvEvQddKQXKoxO9OUc67N71r")
                .build();

        final Token token2 = Token.builder()
                .userId("rowing0328")
                .accessToken("AYIDpalAMvEvQddKQXKoxO9OUc67N71r")
                .build();

        // when & then
        assertThat(token1).isEqualTo(token2);
    }

    @Test
    void 서로_다른_토큰을_비교하면_동등하지_않은_결과를_반환한다() {
        // given
        final Token token1 = Token.builder()
                .userId("rowing0328")
                .accessToken("AYIDpalAMvEvQddKQXKoxO9OUc67N71r")
                .build();

        final Token token2 = Token.builder()
                .userId("codejesterND")
                .accessToken("AYIDpalAMvEvQddKQXKoxO9OUc67N71r")
                .build();

        // when & then
        assertThat(token1).isNotEqualTo(token2);
    }

}
