package com.spring.controller;

import com.spring.annatation.UserServiceReferenceAnnotation;
import com.spring.business.UserBusiness;
import com.spring.service.UserServiceAnnotationService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class UserServiceAnnotationController implements InitializingBean {
    @UserServiceReferenceAnnotation
    private UserServiceAnnotationService userServiceAnnotationService;
    @Autowired
    private UserBusiness userBusiness;
    @Override
    public void afterPropertiesSet() throws Exception {
        String userName = userServiceAnnotationService.getUserName();
        System.out.println("userName = " + userName);
        System.out.println("userBusiness = " + userBusiness.getUserName());
    }
}
