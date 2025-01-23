package org.toastit_v2.common.common.application.config.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.toastit_v2.common.common.application.code.CommonResponseCode;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseCodeAnnotation {

    CommonResponseCode value() default CommonResponseCode.SUCCESS;

}
