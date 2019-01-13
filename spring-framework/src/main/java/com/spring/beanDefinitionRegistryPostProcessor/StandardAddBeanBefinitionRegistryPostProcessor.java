package com.spring.beanDefinitionRegistryPostProcessor;

import com.spring.factoryBean.DefaultUserServiceFactoryBean;
import com.spring.factoryBean.UserServiceFactoryBeanInterface;
import com.spring.lifecycle.LocalLifecycleProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.PriorityOrdered;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

public class StandardAddBeanBefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {
    public static final String LIFECYCLE_PROCESSOR_BEAN_NAME = "lifecycleProcessor";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("Standard Add BeanBefinitionRegistryPostProcessor");
        BeanDefinitionBuilder builder = rootBeanDefinition(RegistryBeanFactoryPostProcessor.class);
        registry.registerBeanDefinition("RegistryBeanFactoryPostProcessor", builder.getBeanDefinition());
        BeanDefinitionBuilder b = rootBeanDefinition(LocalLifecycleProcessor.class);
        registry.registerBeanDefinition(LIFECYCLE_PROCESSOR_BEAN_NAME, b.getBeanDefinition());
        BeanDefinitionBuilder a = BeanDefinitionBuilder.genericBeanDefinition(DefaultUserServiceFactoryBean.class);
        a.addConstructorArgValue(UserServiceFactoryBeanInterface.class);
        registry.registerBeanDefinition("userServiceFactoryBeanInterface",a.getBeanDefinition());
//        a.setFactoryMethod();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("Standard Add BeanFactoryPostProcessor");
    }

    @Override
    public int getOrder() {
        return 3;
    }
}

class RegistryBeanFactoryPostProcessor implements BeanFactoryPostProcessor, PriorityOrdered {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("RegistryBeanFactoryPostProcessor postProcessBeanFactory call");
    }

    @Override
    public int getOrder() {
        return -1;
    }
}