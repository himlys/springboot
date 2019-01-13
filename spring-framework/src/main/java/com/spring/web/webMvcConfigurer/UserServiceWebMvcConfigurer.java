package com.spring.web.webMvcConfigurer;

import com.spring.web.returnValueHandlers.UserServiceHandlerMethodReturnValueHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class UserServiceWebMvcConfigurer implements WebMvcConfigurer {
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new UserServiceHandlerMethodReturnValueHandler());
    }
}
