package com.spring.aop.aspectj.advisor;

import com.spring.annatation.UserServiceProxyMethodAnnotation;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Service;

@Service
public class UserServiceRoundAnnotationMethodMatchingPointcutAdvisor implements PointcutAdvisor {
    @Override
    public Pointcut getPointcut() {
//        还可以去组合ClassAnnotationType
        return new AnnotationMatchingPointcut(null, UserServiceProxyMethodAnnotation.class);
    }

    @Override
    public MethodInterceptor getAdvice() {
        return (MethodInvocation invocation) -> {
            System.out.println("UserServiceRoundAnnotationMethodMatchingPointcutAdvisor before");
            Object result = invocation.getMethod().invoke(invocation.getThis(), invocation.getArguments());
            System.out.println("UserServiceRoundAnnotationMethodMatchingPointcutAdvisor after");
            return result;
        };
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}
