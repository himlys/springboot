package com.spring.beanDefinitionRegistryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

public class ProgramBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("programBeanDefinitionRegistryPostProcessor postProcessBeanDefinitionRegistry PriorityOrdered call");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("programBeanDefinitionRegistryPostProcessor postProcessBeanFactory PriorityOrdered call");
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
class ProgramBeanDefinitionRegistryPostProcessor1 implements BeanDefinitionRegistryPostProcessor, Ordered {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("programBeanDefinitionRegistryPostProcessor postProcessBeanDefinitionRegistry Ordered call");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("programBeanDefinitionRegistryPostProcessor postProcessBeanFactory Ordered call");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
