package com.spring.aop.service.impl;

import com.spring.aop.service.UserServiceProxyXML;
import org.springframework.stereotype.Service;

@Service
public class UserServiceProxyXMLImpl implements UserServiceProxyXML {
    @Override
    public void sayHi(String name) {
        System.out.println("UserServiceProxyXMLImpl " + name);
    }
}
