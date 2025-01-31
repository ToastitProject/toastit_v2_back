package org.toastit_v2.core.domain.auth.security;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {

    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

}
