package org.toastit_v2.common.infra.config.security.core;

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
