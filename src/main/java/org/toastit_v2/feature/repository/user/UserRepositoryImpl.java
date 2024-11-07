package org.toastit_v2.feature.repository.user;

import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.toastit_v2.common.response.code.CommonExceptionCode;
import org.toastit_v2.common.response.exception.RestApiException;
import org.toastit_v2.feature.domain.user.User;
import org.toastit_v2.feature.model.entity.user.UserEntity;
import org.toastit_v2.feature.service.user.port.UserRepository;
import org.springframework.stereotype.Repository;

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
