package org.toastit_v2.common.annotation.swagger;

import io.swagger.v3.oas.annotations.media.Content;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiRequestBody {

    String description() default "";

    boolean required() default true;

    Content[] content() default @Content(mediaType = "application/json");

}
