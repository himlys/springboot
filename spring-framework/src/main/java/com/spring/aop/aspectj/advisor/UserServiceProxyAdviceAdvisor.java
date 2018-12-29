package com.spring.aop.aspectj.advisor;

import com.spring.aop.aspectj.advice.UserServiceProxyAdvice;
import com.spring.aop.service.impl.UserServiceProxyXMLImpl;
import org.aopalliance.aop.Advice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

//@Service
public class UserServiceProxyAdviceAdvisor implements PointcutAdvisor {
    @Override
    public Pointcut getPointcut() {
        return new Pointcut() {
            @Override
            public ClassFilter getClassFilter() {
                return new ClassFilter() {
                    @Override
                    public boolean matches(Class<?> clazz) {
                        return UserServiceProxyXMLImpl.class.isAssignableFrom(clazz);
                    }
                };
            }

            @Override
            public MethodMatcher getMethodMatcher() {
                return new MethodMatcher() {
                    @Override
                    public boolean matches(Method method, Class<?> targetClass) {
                        return true;
                    }

                    @Override
                    public boolean isRuntime() {
                        return true;
                    }

                    @Override
                    public boolean matches(Method method, Class<?> targetClass, Object... args) {
                        return true;
                    }
                };
            }
        };
    }

    @Override
    public Advice getAdvice() {
        return new UserServiceProxyAdvice();
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}
