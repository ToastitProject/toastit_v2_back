package org.toastit_v2.feature.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.toastit_v2.core.security.domain.Authority;
import org.toastit_v2.feature.user.web.request.UserUpdateNicknameRequest;

@Getter
public class User {

    private final Long id;
    private final String email;
    private final String nickname;
    private final String password;
    private final String imageUrl;
    private final UserStatus status;
    private final Authority authority;

    @Builder
    public User(Long id, String email, String nickname, String password, String imageUrl, UserStatus status, Authority authority) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.imageUrl = imageUrl;
        this.status = status;
        this.authority = authority;
    }

    public User update(String password) {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .password(password)
                .imageUrl(this.imageUrl)
                .status(this.status)
                .authority(this.authority)
                .build();
    }

    public User update(UserUpdateNicknameRequest request) {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .nickname(request.getNickname())
                .password(this.password)
                .imageUrl(this.imageUrl)
                .status(this.status)
                .authority(this.authority)
                .build();
    }

}
