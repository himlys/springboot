package com.spring.factoryBean.impl;

import com.spring.factoryBean.UserServiceFactoryBeanTarget;
import org.springframework.stereotype.Service;

@Service
public class UserServiceFactoryBeanTargetImpl implements UserServiceFactoryBeanTarget {

    @Override
    public void sayHiUseUserService() {
        System.out.println("it's me target sayHi to you");
    }
}
