package com.java.web.common.annotation;

import com.java.web.common.route.HttpMethod;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PatchMapping {
    String value() default "/";
    HttpMethod method() default HttpMethod.PATCH;
}
