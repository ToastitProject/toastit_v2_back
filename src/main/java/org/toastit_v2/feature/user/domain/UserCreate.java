package org.toastit_v2.feature.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.toastit_v2.core.security.domain.Authority;
import org.toastit_v2.feature.user.application.util.RandomNickname;
import org.toastit_v2.feature.user.web.request.UserJoinRequest;

@Getter
public class UserCreate {

    private final String email;
    private final String nickname;
    private final String password;
    private final UserStatus status;
    private final Authority authority;

    @Builder
    public UserCreate(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.status = UserStatus.ACTIVE;
        this.authority = Authority.USER;
    }

    public static UserCreate from(UserJoinRequest request, PasswordEncoder passwordEncoder) {
        return UserCreate.builder()
                .email(request.getEmail())
                .nickname(RandomNickname.generate())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    public User toUser() {
        return User.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(this.password)
                .status(this.status)
                .authority(this.authority)
                .build();
    }

}
