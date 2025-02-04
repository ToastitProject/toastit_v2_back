package org.toastit_v2.common.annotation.swagger;

import org.toastit_v2.common.response.code.SuccessCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiSuccessResponse {

    SuccessCode value() default SuccessCode.SUCCESS;

}
