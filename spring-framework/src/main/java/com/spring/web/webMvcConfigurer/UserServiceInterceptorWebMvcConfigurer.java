package com.spring.web.webMvcConfigurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class UserServiceInterceptorWebMvcConfigurer implements WebMvcConfigurer {
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter(){
            /**
             * This implementation always returns {@code true}.
             */
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {
                System.out.println("HandlerInterceptorAdapter request in ");
                return true;
            }

            /**
             * This implementation is empty.
             */
            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                                   @Nullable ModelAndView modelAndView) throws Exception {
                System.out.println("HandlerInterceptorAdapter response out ");
            }
        });
        registry.addWebRequestInterceptor(new WebRequestInterceptor() {
            @Override
            public void preHandle(WebRequest request) throws Exception {
                System.out.println("WebRequestInterceptor request in ");
            }

            @Override
            public void postHandle(WebRequest request, ModelMap model) throws Exception {
                System.out.println("WebRequestInterceptor response out ");
            }

            @Override
            public void afterCompletion(WebRequest request, Exception ex) throws Exception {

            }
        });
    }
}
