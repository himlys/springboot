package com.spring.initializer;

import com.spring.beanDefinitionRegistryPostProcessor.ApplicationContextInitializerAddBeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

public class MyContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.addBeanFactoryPostProcessor(new ApplicationContextInitializerAddBeanDefinitionRegistryPostProcessor());
        System.out.println("init MyContextInitializer !");
    }
}