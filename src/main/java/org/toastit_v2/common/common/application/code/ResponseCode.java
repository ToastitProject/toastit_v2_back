package org.toastit_v2.common.common.application.code;

import org.springframework.http.HttpStatus;

public interface ResponseCode {

    String name();

    HttpStatus getHttpStatus();

    String getData();
}
