package com.spring.web.webMvcConfigurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ContentNegotiationWebMvcConfigurer implements WebMvcConfigurer {
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.defaultContentTypeStrategy(new HeaderContentNegotiationStrategy());
        List<ContentNegotiationStrategy> list = new ArrayList();
        list.add(new HeaderContentNegotiationStrategy());
        configurer.strategies(list);
//        先判定strategies，如果他有，其他的就过了。如果没有这个，并且忽略head的话，会添加defaultContentTypeStrategy
    }
}
