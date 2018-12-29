package com.spring.beanDefinitionRegistryPostProcessor;

import com.spring.service.UserServiceSupplier;
import com.spring.service.impl.UserServiceSupplierImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.PriorityOrdered;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

public class ApplicationContextInitializerAddBeanDefinitionRegistryPostProcessor
        implements BeanDefinitionRegistryPostProcessor, PriorityOrdered {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("ApplicationContextInitializer added BeanDefinitionRegistryPostProcessor call");
        BeanDefinitionBuilder builder = rootBeanDefinition(ProgramBeanDefinitionRegistryPostProcessor.class);
        registry.registerBeanDefinition("com.spring.beanDefinitionRegistryPostProcessor.ProgramBeanDefinitionRegistryPostProcessor", builder.getBeanDefinition());
        BeanDefinitionBuilder b1 = rootBeanDefinition(ProgramBeanDefinitionRegistryPostProcessor1.class);
        registry.registerBeanDefinition("com.spring.beanDefinitionRegistryPostProcessor.ProgramBeanDefinitionRegistryPostProcessor1", b1.getBeanDefinition());
        BeanDefinitionBuilder b2 = rootBeanDefinition(UserServiceSupplierImpl.class);
        BeanDefinition definition = b2.getBeanDefinition();
        ((AbstractBeanDefinition) definition).setInstanceSupplier(() -> {
            UserServiceSupplier userServiceSupplier =  new UserServiceSupplierImpl();
            System.out.println("userServiceSupplier supplier init");
            return userServiceSupplier;
        });
        registry.registerBeanDefinition("UserServiceSupplierImpl", b2.getBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("ApplicationContextInitializer added BeanFactoryPostProcessor call");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
