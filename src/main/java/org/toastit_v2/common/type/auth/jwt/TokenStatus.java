package org.toastit_v2.common.type.auth.jwt;

import lombok.Getter;

@Getter
public enum TokenStatus {

    AUTHENTICATED,
    EXPIRED,
    INVALID

}
