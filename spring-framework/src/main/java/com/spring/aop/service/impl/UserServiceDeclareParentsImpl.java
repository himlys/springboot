package com.spring.aop.service.impl;

import com.spring.aop.service.UserServiceDeclareParents;
import org.springframework.stereotype.Service;

@Service
public class UserServiceDeclareParentsImpl implements UserServiceDeclareParents {
    public void sayHi(){
        System.out.println("UserServiceDeclareParentsImpl sayHI");
    }
}
