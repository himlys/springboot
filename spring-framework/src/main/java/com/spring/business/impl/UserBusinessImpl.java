package com.spring.business.impl;

import com.spring.annatation.UserServiceReferenceAnnotation;
import com.spring.business.UserBusiness;
import com.spring.service.UserService;
import com.spring.service.UserServiceAnnotationService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@DependsOn("userService")
public class UserBusinessImpl implements UserBusiness , InitializingBean {
    AnnotationMetadata importMetadata;
    @Autowired
    private UserService userService;
    @UserServiceReferenceAnnotation
    private UserServiceAnnotationService userServiceAnnotationService;
    @Resource(name = "userServiceEarlyBeanImpl")
    private UserService userServiceEarly;
    public String getUserName() {
        return userServiceAnnotationService.getUserName();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        userService.setUserName("UserBusinessImpl set User Name");
    }
}
