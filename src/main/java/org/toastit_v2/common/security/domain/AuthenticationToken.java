package org.toastit_v2.common.security.domain;

import java.util.List;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private String token;
    private Object principal;
    private Object credentials;

    public AuthenticationToken(List<Authority> authorities, Object principal, Object credentials) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(true);
    }

    public AuthenticationToken(String token) {
        super(null);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

}
