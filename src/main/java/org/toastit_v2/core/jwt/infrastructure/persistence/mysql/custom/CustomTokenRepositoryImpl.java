package org.toastit_v2.core.jwt.infrastructure.persistence.mysql.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.toastit_v2.core.jwt.domain.Token;
import org.toastit_v2.core.jwt.infrastructure.persistence.mysql.entity.QTokenEntity;

@Repository
@RequiredArgsConstructor
public class CustomTokenRepositoryImpl implements CustomTokenRepository {

    private final JPAQueryFactory queryFactory;

    private final QTokenEntity qToken = QTokenEntity.tokenEntity;

    @Override
    public Long update(Token token) {
        return queryFactory.update(qToken)
                .set(qToken.accessToken, token.getAccessToken())
                .set(qToken.refreshToken, token.getRefreshToken())
                .set(qToken.grantType, token.getGrantType())
                .where(qToken.userId.eq(token.getUserId()))
                .execute();
    }

    @Override
    public Long updateByRefreshToken(String refreshToken, String accessToken) {
        return queryFactory.update(qToken)
                .set(qToken.accessToken, accessToken)
                .where(qToken.refreshToken.eq(refreshToken))
                .execute();
    }

}
