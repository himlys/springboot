package com.spring.business.impl;

import com.spring.annatation.UserServiceReferenceAnnotation;
import com.spring.business.UserBusiness;
import com.spring.data.entity.UserServiceEntity;
import com.spring.data.jdbc.repositories.UserServiceRepository;
import com.spring.factoryBean.UserServiceFactoryBeanInterface;
import com.spring.service.UserService;
import com.spring.service.UserServiceAnnotationService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Random;

@Service
@DependsOn("userService")
public class UserBusinessImpl implements UserBusiness, InitializingBean {
    AnnotationMetadata importMetadata;
    @Autowired
    private UserService userService;
    @UserServiceReferenceAnnotation
    private UserServiceAnnotationService userServiceAnnotationService;
    @Resource(name = "userServiceEarlyBeanImpl")
    private UserService userServiceEarly;
    @Autowired
    UserServiceFactoryBeanInterface userServiceFactoryBeanInterface;
    @Autowired
    UserServiceRepository userServiceRepository;

    public String getUserName() {
        return userServiceAnnotationService.getUserName();
    }

    @Override
    @Transactional
    public int insertUserService(UserServiceEntity entity) {
        return userServiceRepository.save(entity.getId(), entity.getName());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        UserServiceEntity entity = new UserServiceEntity(new Random().nextInt(), "leader");
//        userServiceRepository.save(entity.getId(), entity.getName());
//        userServiceFactoryBeanInterface.sayHiUseUserService();
        userService.setUserName("UserBusinessImpl set User Name");
    }
}
