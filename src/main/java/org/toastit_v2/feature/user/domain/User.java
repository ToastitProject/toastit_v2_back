package org.toastit_v2.feature.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.feature.user.infrastructure.persistence.mysql.entity.UserEntity;

@Getter
public class User {

    private final Long id;

    private final String email;

    private final String nickname;

    private final String password;

    private final String imageUrl;

    private final Authority authority;

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
