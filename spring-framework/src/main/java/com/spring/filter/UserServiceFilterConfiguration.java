package com.spring.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceFilterConfiguration {
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        UserServiceFilter filter = new UserServiceFilter();
        FilterRegistrationBean<UserServiceFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
