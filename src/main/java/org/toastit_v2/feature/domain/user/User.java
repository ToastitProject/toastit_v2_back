package org.toastit_v2.feature.domain.user;

import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.common.infra.config.security.core.Authority;
import org.toastit_v2.feature.model.entity.user.UserEntity;

@Getter
public class User {

    private final Long id;

    private final String email;

    private String nickname;

    private String password;

    private String imageUrl;

    private Authority authority;

    @Builder
    public User(Long id, String email, String nickname, String password, String imageUrl, Authority authority) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.imageUrl = imageUrl;
        this.authority = authority;
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .imageUrl(this.imageUrl)
                .authority(this.authority)
                .build();
    }

}
