package com.spring.service.impl;

import com.spring.annatation.UserServiceAnnotation;
import com.spring.service.UserServiceAnnotationService;

@UserServiceAnnotation(userName = "Trump",name = "UserServiceAnnotationService")
public class UserServiceAnnotationServiceImpl implements UserServiceAnnotationService {
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserName() {
        return userName;
    }
}
