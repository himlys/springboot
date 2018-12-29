package com.spring.smartInstantiationAwareBeanPostProcessor;

import com.spring.service.UserService;
import com.spring.service.impl.UserServiceEarlyBeanImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

public class UserServiceSmartInstantiationAwareBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
