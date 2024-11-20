package org.toastit_v2.feature.user.infrastructure.persistence.mysql.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.toastit_v2.feature.user.domain.UserStatus;
import org.toastit_v2.feature.user.infrastructure.persistence.mysql.QUserEntity;

@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final JPAQueryFactory queryFactory;

    private final QUserEntity qUser = QUserEntity.userEntity;

    @Override
    public long update(Long userId, UserStatus status) {
        return queryFactory.update(qUser)
                .set(qUser.status, status)
                .where(qUser.id.eq(userId))
                .execute();
    }

}
