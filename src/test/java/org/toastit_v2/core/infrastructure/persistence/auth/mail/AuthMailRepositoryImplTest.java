package org.toastit_v2.core.infrastructure.persistence.auth.mail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.toastit_v2.core.application.auth.mail.port.AuthMailRepository;
import org.toastit_v2.core.domain.auth.mail.AuthMail;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        final String userEmail = "test@example.com";
        final String authNumber = "123456";
        final LocalDateTime created = LocalDateTime.now();
        final AuthMail authMail = AuthMail.create(userEmail, () -> authNumber, () -> created);

        // when
        authMailRepository.save(authMail);

        // then
        final Optional<AuthMail> expected = authMailRepository.findById(userEmail);
        assertThat(expected).isPresent();
        assertThat(expected.get().getAuthCode()).isEqualTo(authNumber);
    }

}
