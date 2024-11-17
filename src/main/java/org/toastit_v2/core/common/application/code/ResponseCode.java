package org.toastit_v2.core.common.application.code;

import org.springframework.http.HttpStatus;

public interface ResponseCode {

    String name();

    HttpStatus getHttpStatus();

    String getData();
}
