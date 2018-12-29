package com.spring.service.impl;

import com.spring.business.UserBusiness;
import com.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userServiceEarlyBeanImpl")
public class UserServiceEarlyBeanImpl implements UserService {
    @Autowired
    private UserBusiness userBusiness;

    @Override
    public void setUserName(String userName) {
        System.out.println("UserServiceEarlyBeanImpl userName = " + userName);
    }
}
