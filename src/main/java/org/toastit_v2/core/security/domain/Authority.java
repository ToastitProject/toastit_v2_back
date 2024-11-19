package org.toastit_v2.core.security.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {

    ADMIN,
    USER;

    /**
     * 유저의 권한을 String으로 반환하는 메소드
     * @return authority name
     */
    @Override
    public String getAuthority() {
        return name();
    }

}
