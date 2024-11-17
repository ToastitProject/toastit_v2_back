package org.toastit_v2.feature.user.infrastructure.persistence.mysql;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.toastit_v2.feature.user.domain.User;
import org.toastit_v2.feature.user.application.port.UserRepository;
import org.springframework.stereotype.Repository;
import org.toastit_v2.feature.user.infrastructure.persistence.mysql.entity.UserEntity;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository repository;

    @Override
    public Optional<User> findByNickname(String nickname) {
        return repository.findByNickname(nickname).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(UserEntity::toModel);
    }

//    private <T> User getByCriteria(Function<T, Optional<UserEntity>> finderFunction, T value) {
//        return finderFunction.apply(value)
//                .map(UserEntity::toModel)
//                .orElseThrow(
//                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_USER)
//                );
//    }

}
