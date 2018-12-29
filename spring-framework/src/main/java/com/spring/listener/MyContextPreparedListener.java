package com.spring.listener;

import com.spring.beanDefinitionRegistryPostProcessor.ContextPreparedAddBeanDefinitionRegistryPostProcessor;
import com.spring.context.event.MyApplicationContextPreparedEvent;
import org.springframework.context.ApplicationListener;

public class MyContextPreparedListener implements ApplicationListener<MyApplicationContextPreparedEvent> {
    @Override
    public void onApplicationEvent(MyApplicationContextPreparedEvent event) {
        event.getContext().addBeanFactoryPostProcessor(new ContextPreparedAddBeanDefinitionRegistryPostProcessor());
    }
}
