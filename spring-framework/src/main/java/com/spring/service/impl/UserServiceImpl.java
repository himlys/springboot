package com.spring.service.impl;

import com.spring.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Override
    public void setUserName(String userName) {
        System.out.println("set User Name!");
    }
}
