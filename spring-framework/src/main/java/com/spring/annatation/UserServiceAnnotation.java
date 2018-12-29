package com.spring.annatation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface UserServiceAnnotation {
    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String userName() default "Tom Leader";
}
