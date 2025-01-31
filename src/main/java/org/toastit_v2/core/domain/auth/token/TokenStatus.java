package org.toastit_v2.core.domain.auth.token;

import lombok.Getter;

@Getter
public enum TokenStatus {

    AUTHENTICATED,
    EXPIRED,
    INVALID

}
