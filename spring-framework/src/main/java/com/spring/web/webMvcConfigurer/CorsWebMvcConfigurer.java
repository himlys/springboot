package com.spring.web.webMvcConfigurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// 当然，也可以用这个WebMvcConfigurerAdapter，不过不建议使用了。
@Configuration
public class CorsWebMvcConfigurer implements WebMvcConfigurer {
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/abc");
    }
}
