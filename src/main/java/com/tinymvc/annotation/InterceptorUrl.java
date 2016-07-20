package com.tinymvc.annotation;

/**
 * Created by shenwenbo on 16/7/3.
 */
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface InterceptorUrl {
    String value() default "";
}
