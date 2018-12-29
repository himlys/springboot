package com.spring;

import com.spring.annatation.UserServiceComponentScan;
import com.spring.web.servlet.context.MyAnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = "com.spring")
@UserServiceComponentScan(path = "com.spring")
@ImportResource("META-INF/application-aop.xml")
@EnableAspectJAutoProxy
public class SpringApp {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringApp.class);
        springApplication.setApplicationContextClass(MyAnnotationConfigServletWebServerApplicationContext.class);
        springApplication.run(args);
        System.out.println("Hello World!");

    }
}
