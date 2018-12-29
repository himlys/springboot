package com.spring.annatation;

import com.spring.importselector.UserServiceImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Import(UserServiceImportSelector.class)
public @interface UserServiceComponentScan {
    String[] path();
}
