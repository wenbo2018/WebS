package com.github.wenbo2018.webs.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	String requestUrl() default "";
	String responseUrl() default "";
	String controllerUrl() default "";
}
