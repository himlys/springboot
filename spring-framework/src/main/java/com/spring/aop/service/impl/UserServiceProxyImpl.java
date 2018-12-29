package com.spring.aop.service.impl;

import com.spring.annatation.UserServiceProxyMethodAnnotation;
import com.spring.aop.service.UserServiceProxy;
import org.springframework.stereotype.Service;

@Service
public class UserServiceProxyImpl implements UserServiceProxy {

    @Override
    public void sayHi(String hi) {
        System.out.println("say hi " + hi);
    }

    @Override
    public void sayHi() {
        sayHi("inner say hi ");
        System.out.println("say hi no args ");
    }

    @Override
    @UserServiceProxyMethodAnnotation
    public void sayHiAnnotation() {
        System.out.println("UserServiceProxyImpl proxyMethod");
    }
}
