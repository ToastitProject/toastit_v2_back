package org.toastit_v2.common.security.domain;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long userId;
    private final String email;
    private final String nickname;
    private final String password;
    private final List<Authority> authorities;

    public CustomUserDetails(Long userId, String email, String nickname, String password, List<Authority> authorities) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

}
