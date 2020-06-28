package com.java.web.common.annotation;

import com.java.web.common.route.HttpMethod;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PostMapping {
    String value() default "/";
    HttpMethod method() default HttpMethod.POST;
}
