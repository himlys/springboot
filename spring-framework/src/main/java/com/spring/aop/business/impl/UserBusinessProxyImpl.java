package com.spring.aop.business.impl;

import com.spring.aop.business.UserBusinessProxy;
import com.spring.aop.service.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserBusinessProxyImpl implements UserBusinessProxy, InitializingBean {
    @Resource(name = "userServiceProxyImpl")
    private UserServiceProxy userServiceProxy;
    @Resource(name = "userServiceProxyAnnotationClassImpl")
    private UserServiceProxyAnnotationService userServiceProxyAnnotationClassImpl;
    @Autowired
    private UserServiceProxyXML userServiceProxyXML;
@Autowired
private DeclareParentsUserService declareParentsUserService;
    @Override
    public void afterPropertiesSet() throws Exception {
        userServiceProxy.sayHi();
        userServiceProxy.sayHi("hi userServiceProxy");
        ((UserServiceDeclareParents)declareParentsUserService).sayHi();
        userServiceProxy.sayHiAnnotation();
        userServiceProxyAnnotationClassImpl.sayHiAnnotation();
        userServiceProxyXML.sayHi("userServiceProxyXML say hi");
    }
}
