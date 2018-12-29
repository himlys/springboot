package com.spring.aop.aspectj.advisor;

import com.spring.aop.service.UserServiceProxy;
import org.aopalliance.aop.Advice;
import org.springframework.aop.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class UserServiceBeforeAdvisor implements PointcutAdvisor {
    @Override
    public Pointcut getPointcut() {
        return new Pointcut() {
            @Override
            public ClassFilter getClassFilter() {
                return new ClassFilter() {
                    @Override
                    public boolean matches(Class<?> clazz) {
                        return UserServiceProxy.class.isAssignableFrom(clazz);
                    }
                };
            }

            @Override
            public MethodMatcher getMethodMatcher() {
                return new MethodMatcher() {
                    @Override
                    public boolean matches(Method method, Class<?> targetClass) {
                        if (method.getName().equals("sayHi") && method.getParameterTypes().length > 0) return true;
                        return false;
                    }

                    @Override
                    public boolean isRuntime() {
                        return false;
                    }

                    @Override
                    public boolean matches(Method method, Class<?> targetClass, Object... args) {
                        if (method.getName().equals("sayHi") && args.length == 1) return true;
                        return false;
                    }
                };
            }
        };
    }

    @Override
    public Advice getAdvice() {
        return new MethodBeforeAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println("UserServiceBeforeAdvisor before");
            }
        };
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}
