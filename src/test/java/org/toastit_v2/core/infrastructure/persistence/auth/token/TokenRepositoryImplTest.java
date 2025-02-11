package org.toastit_v2.core.infrastructure.persistence.auth.token;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.toastit_v2.core.application.auth.token.port.TokenRepository;
import org.toastit_v2.core.domain.auth.token.Token;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.toastit_v2.common.fixture.auth.TokenFixture.DEFAULT_TOKEN;
import static org.toastit_v2.common.fixture.auth.TokenFixture.DEFAULT_USER_ID;

@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TokenRepositoryImplTest {

    private final TokenRepository tokenRepository;
    private final TokenCrudRepository tokenCrudRepository;

    TokenRepositoryImplTest(final TokenRepository tokenRepository, final TokenCrudRepository tokenCrudRepository) {
        this.tokenRepository = tokenRepository;
        this.tokenCrudRepository = tokenCrudRepository;
    }

    @AfterEach
    void tearDown() {
        tokenCrudRepository.deleteAll();
    }

    @Test
    void 토큰을_저장하고_조회한다() {
        // given
        final Token request = Token.create(DEFAULT_USER_ID, DEFAULT_TOKEN);

        // when
        tokenRepository.save(request);

        // then
        final Optional<Token> response = tokenRepository.findById(DEFAULT_USER_ID);
        assertThat(response).isPresent();
        assertThat(response.get().getAccessToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @Test
    void 토큰을_삭제한다() {
        // given
        final Token request = Token.create(DEFAULT_USER_ID, DEFAULT_TOKEN);
        tokenRepository.save(request);

        // when
        tokenRepository.deleteById(DEFAULT_USER_ID);

        // then
        Optional<Token> response = tokenRepository.findById(DEFAULT_USER_ID);
        assertThat(response).isEmpty();
    }

}
