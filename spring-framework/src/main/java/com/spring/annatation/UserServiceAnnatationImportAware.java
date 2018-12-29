package com.spring.annatation;

import com.spring.config.BeanPostProcessorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Import(BeanPostProcessorConfiguration.class)
public @interface UserServiceAnnatationImportAware {
    String importAware() default "default importaware";
}
