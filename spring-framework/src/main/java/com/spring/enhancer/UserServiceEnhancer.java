package com.spring.enhancer;

import com.spring.aop.service.UserServiceProxy;
import com.spring.aop.service.impl.UserServiceProxyImpl;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;

public class UserServiceEnhancer {
    public static void main(String args[]) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserServiceProxyImpl.class);
        enhancer.setCallback(new MethodInterceptorImpl());
        UserServiceProxy m = (UserServiceProxy) enhancer.create();


        m.sayHiAnnotation();
    }

    private static class MethodInterceptorImpl implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("Before invoke " + method);
            Object result = methodProxy.invokeSuper(o, objects);
            System.out.println("After invoke" + method);
            return result;
        }
    }
}
