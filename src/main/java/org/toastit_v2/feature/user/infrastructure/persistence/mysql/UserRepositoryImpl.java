package org.toastit_v2.feature.user.infrastructure.persistence.mysql;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.toastit_v2.common.common.application.code.CommonExceptionCode;
import org.toastit_v2.common.common.application.exception.RestApiException;
import org.toastit_v2.feature.user.domain.User;
import org.toastit_v2.feature.user.application.port.UserRepository;
import org.springframework.stereotype.Repository;
import org.toastit_v2.feature.user.domain.UserStatus;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository repository;

    @Override
    public Optional<User> findById(Long id) {
        return repository.findByIdAndStatus(id, UserStatus.ACTIVE).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmailAndStatus(email, UserStatus.ACTIVE).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return repository.findByNicknameAndStatus(nickname, UserStatus.ACTIVE).map(UserEntity::toModel);
    }

    @Override
    public User getUserById(Long id) {
        return findById(id).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
        );
    }

    @Override
    public void save(User user) {
        repository.save(UserEntity.from(user));
    }

    @Override
    public void update(long id, UserStatus status) {
        if (repository.update(id, status) < 0) {
            throw new RestApiException(CommonExceptionCode.UPDATE_FAIL_USER);
        }
    }

}
