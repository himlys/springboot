package com.spring.beanFactoryPostProcessor;

import com.spring.service.impl.UserServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;

public class StandardAddBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("StandardAddBeanFactoryPostProcessor postProcessBeanFactory call");
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        if (beanDefinition instanceof AbstractBeanDefinition) {
            ((AbstractBeanDefinition) beanDefinition).setInstanceSupplier(() -> {
                return new UserServiceImpl();
            });
        }
    }
}