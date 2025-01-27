package org.toastit_v2.core.domain.auth.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.toastit_v2.core.domain.member.Member;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final String userId;
    private final List<GrantedAuthority> roles;

    private CustomUserDetails(final Member member) {
        this.userId = member.getUserId();
        this.roles = List.of(member.getAuthority());
    }

    public static CustomUserDetails from(final Member member) {
        return new CustomUserDetails(member);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableList(roles);
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return getUserId();
    }

}
