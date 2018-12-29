package com.spring.aop.aspectj.advisor;

import com.spring.annatation.UserServiceProxyClassAnnotation;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Service;

@Service
public class UserServiceRoundAnnotationClassMatchingPointcutAdvisor implements PointcutAdvisor {
    @Override
    public Pointcut getPointcut() {
        return new AnnotationMatchingPointcut(UserServiceProxyClassAnnotation.class);
    }

    @Override
    public MethodInterceptor getAdvice() {
        return (MethodInvocation invocation) -> {
            System.out.println("UserServiceRoundAnnotationClassMatchingPointcutAdvisor before");
            Object result = invocation.getMethod().invoke(invocation.getThis(), invocation.getArguments());
            System.out.println("UserServiceRoundAnnotationClassMatchingPointcutAdvisor after");
            return result;
        };
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}
