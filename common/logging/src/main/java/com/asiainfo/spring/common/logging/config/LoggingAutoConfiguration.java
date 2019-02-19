package com.asiainfo.spring.common.logging.config;

import com.alibaba.fastjson.JSON;
import com.asiainfo.spring.common.logging.logback.BaseLoggingObject;
import com.asiainfo.spring.common.logging.logback.Logger;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.MDC;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = false)
public class LoggingAutoConfiguration {
    @Configuration
    @Import(LoggingMDCFilter.class)
    class MDCFilterConfiguration {

    }

    @Configuration
    static class WebServerInfo implements ApplicationListener<ServletWebServerInitializedEvent> {
        private static ServletWebServerInitializedEvent event;

        @Override
        public void onApplicationEvent(ServletWebServerInitializedEvent event) {
            this.event = event;
        }

        public static Integer getPort() {
            return event.getWebServer().getPort();
        }

        public static String getHostPort() {
            String host = "";
            try {
                host = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return host + ":" + event.getWebServer().getPort();
        }
    }

    @Bean
    public PointcutAdvisor beforePointcutAdvisor() {
        return new PointcutAdvisor() {

            @Override
            public Advice getAdvice() {
                return new MethodInterceptor() {
                    @Override
                    public Object invoke(MethodInvocation invocation) throws Throwable {
                        Logger logger = ((BaseLoggingObject) invocation.getThis()).getLogger();
                        logger.debug("{} 函数start", invocation.getMethod().getName());
                        int i = 0;
                        for (Object object : invocation.getArguments()) {
                            if (object != null) {
                                if (object instanceof HttpServletRequest || object instanceof HttpServletResponse) {
                                    continue;
                                } else {
                                    try {
                                        String json = JSON.toJSONString(object);
                                        logger.debug("参数{} {}", i++, json);
                                    } catch (Exception e) {
                                        logger.debug("参数{} {}", i++, "解析返回节点失败");
                                    }

                                }
                            }
                        }
                        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                        if (attributes != null) {
                            HttpServletRequest request = attributes.getRequest();
                            request.setAttribute("current_logger", logger);
                            request.setAttribute("current_method", invocation.getMethod().getName());
                        }
                        Object obj = null;
                        try {
                            obj = invocation.proceed();
                            logger.debug("{} 函数end 返回：{}", invocation.getMethod().getName(), obj != null ? JSON.toJSONString(obj) : "");
                            return obj;
                        } catch (Throwable throwable) {
                            logger.debug("{} 函数end 返回：{}", invocation.getMethod().getName(), obj != null ? "解析返回节点失败" : "");
                            return obj;
                        }
                    }
                };
            }

            @Override
            public boolean isPerInstance() {
                return false;
            }

            @Override
            public Pointcut getPointcut() {
                return new Pointcut() {

                    @Override
                    public ClassFilter getClassFilter() {
                        return new ClassFilter() {
                            @Override
                            public boolean matches(Class<?> clazz) {
                                if (BaseLoggingObject.class.isAssignableFrom(clazz)) {
                                    return true;
                                }
                                return false;
                            }
                        };
                    }

                    @Override
                    public MethodMatcher getMethodMatcher() {
                        return new MethodMatcher() {
                            @Override
                            public boolean matches(Method method, Class<?> targetClass) {
                                if (BaseLoggingObject.class.isAssignableFrom(targetClass)) {
                                    return true;
                                }
                                return false;
                            }

                            @Override
                            public boolean isRuntime() {
                                return true;
                            }

                            @Override
                            public boolean matches(Method method, Class<?> targetClass, Object... args) {
                                if (BaseLoggingObject.class.isAssignableFrom(targetClass)) {
                                    return true;
                                }
                                return false;
                            }
                        };
                    }
                };
            }
        };
    }
}

@WebFilter(filterName = "logMDCFilter", urlPatterns = "/**")
@Order(1)
class LoggingMDCFilter extends OncePerRequestFilter implements Filter {
    @Value("${spring.application.name}")
    private String applicationName;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            MDC.put("server_name", applicationName);
            MDC.put("server_host_port", LoggingAutoConfiguration.WebServerInfo.getHostPort());
            chain.doFilter(request, response);
        } finally {

        }
    }
}
