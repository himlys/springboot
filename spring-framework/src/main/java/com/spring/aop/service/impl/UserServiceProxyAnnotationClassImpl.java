package com.spring.aop.service.impl;

import com.spring.annatation.UserServiceProxyClassAnnotation;
import com.spring.aop.service.UserServiceProxyAnnotationService;
import org.springframework.stereotype.Service;

@Service
@UserServiceProxyClassAnnotation
public class UserServiceProxyAnnotationClassImpl implements UserServiceProxyAnnotationService {
    @Override
    public void sayHi(String hi) {

    }

    @Override
    public void sayHi() {

    }

    @Override
    public void sayHiAnnotation() {
        System.out.println("say hi Annotation");
    }
}
