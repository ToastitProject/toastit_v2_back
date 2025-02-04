package org.toastit_v2.core.infrastructure.persistence.auth.token;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.toastit_v2.core.application.auth.token.port.TokenRepository;
import org.toastit_v2.core.domain.auth.token.Token;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class TokenRepositoryImplTest {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenCrudRepository tokenCrudRepository;

    @AfterEach
    void tearDown() {
        tokenCrudRepository.deleteAll();
    }

    @Test
    void 토큰을_저장하고_조회한다() {
        // given
        final String userId = "rowing0328";
        final String accessToken = "AYIDpalAMvEvQddKQXKoxO9OUc67N71r";
        final Token token = Token.create(userId, accessToken);

        // when
        tokenRepository.save(token);

        // then
        final Optional<Token> excepted = tokenRepository.findById(userId);
        assertThat(excepted).isPresent();
        assertThat(excepted.get().getAccessToken()).isEqualTo(accessToken);
    }

    @Test
    void 토큰을_삭제한다() {
        // given
        final String userId = "rowing0328";
        final String accessToken = "AYIDpalAMvEvQddKQXKoxO9OUc67N71r";
        final Token token = Token.create(userId, accessToken);
        tokenRepository.save(token);

        // when
        tokenRepository.deleteById(userId);

        // then
        Optional<Token> excepted = tokenRepository.findById(userId);
        assertThat(excepted).isEmpty();
    }

}
