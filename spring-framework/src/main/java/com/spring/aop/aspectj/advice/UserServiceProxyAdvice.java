package com.spring.aop.aspectj.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class UserServiceProxyAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("UserServiceProxyAdvice before");
        Object result = invocation.getMethod().invoke(invocation.getThis(), invocation.getArguments());
        System.out.println("UserServiceProxyAdvice after");
        return result;
    }
    public void before(String name){
        System.out.println("before name = " + name);
    }
}
