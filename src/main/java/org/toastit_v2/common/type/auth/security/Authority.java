package org.toastit_v2.common.type.auth.security;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {

    USER,
    ADMIN;


    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

}
