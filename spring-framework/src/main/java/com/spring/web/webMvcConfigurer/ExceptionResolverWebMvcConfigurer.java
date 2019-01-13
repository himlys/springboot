package com.spring.web.webMvcConfigurer;

import com.alibaba.fastjson.JSONObject;
import com.spring.controller.ExceptionHandlerController1;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Configuration
public class ExceptionResolverWebMvcConfigurer implements WebMvcConfigurer {
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
//        一般情况下，我们不会自己去处理这些异常，让系统自己去配置。一旦我们配置了，系统就不会再自动配置了。
//        resolvers.add(new HandlerExceptionResolver() {
//            @Override
//            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//                Throwable t = ex.getCause();
//                if (t == null || t instanceof RuntimeException) {
//                    System.out.println("返回一个异常");
//                }
//                try {
//                    response.getWriter().write("返回一个异常");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        });
    }

    //    这样添加就没问题了。
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new AbstractHandlerExceptionResolver() {
            protected boolean shouldApplyTo(HttpServletRequest request, @Nullable Object handler) {
                if (handler instanceof HandlerMethod) {
                    if (ExceptionHandlerController1.class.isAssignableFrom(((HandlerMethod) handler).getBean().getClass())) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                return new ModelAndView();
            }
        });
        resolvers.add(new HandlerExceptionResolver() {
            @Override
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
                Throwable t = ex.getCause();
                if ((t == null || t instanceof RuntimeException) && !(ex instanceof ArithmeticException)) {
                    System.out.println("返回一个异常");
                    try {
                        response.setContentType("text/html; charset=UTF-8");
                        response.getWriter().write(new String("返回一个异常"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new ModelAndView();
                }
                return null;
            }
        });
    }

}

@ControllerAdvice
class UserServiceExceptionHandler {
    @ExceptionHandler(ArithmeticException.class)
    public @ResponseBody
    JSONObject handleException() {
        JSONObject object = new JSONObject();
        object.put("result", "0000");
        return object;
    }
}
