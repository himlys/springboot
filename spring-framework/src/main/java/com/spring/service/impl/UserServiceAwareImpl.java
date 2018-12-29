package com.spring.service.impl;

import com.spring.service.UserServiceAware;
import org.springframework.beans.factory.InitializingBean;

public class UserServiceAwareImpl implements UserServiceAware , InitializingBean {
    private final String aware;

    public UserServiceAwareImpl(String aware) {
        this.aware = aware;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("UserServiceAwareImpl aware " + aware);
    }
}
