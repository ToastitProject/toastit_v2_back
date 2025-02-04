package org.toastit_v2.core.infrastructure.persistence.auth.mail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.toastit_v2.core.application.auth.mail.port.AuthMailRepository;
import org.toastit_v2.core.domain.auth.mail.AuthMail;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.toastit_v2.common.fixture.AuthMailFixture.*;

@SpringBootTest
@ActiveProfiles("test")
class AuthMailRepositoryImplTest {

    @Autowired
    private AuthMailRepository authMailRepository;

    @Autowired
    private AuthMailCrudRepository authMailCrudRepository;

    @AfterEach
    void tearDown() {
        authMailCrudRepository.deleteAll();
    }

    @Test
    void 인증_메일을_저장하고_조회한다() {
        // given
        final AuthMail authMail = AuthMail.create(DEFAULT_EMAIL, () -> DEFAULT_AUTH_CODE, () -> DEFAULT_CREATED_AT);

        // when
        authMailRepository.save(authMail);

        // then
        final Optional<AuthMail> expected = authMailRepository.findById(DEFAULT_EMAIL);
        assertThat(expected).isPresent();
        assertThat(expected.get().getAuthCode()).isEqualTo(DEFAULT_AUTH_CODE);
    }

}
