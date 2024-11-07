package org.toastit_v2.feature.repository.jwt.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.toastit_v2.feature.domain.jwt.Token;
import org.toastit_v2.feature.model.entity.jwt.QTokenEntity;

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
