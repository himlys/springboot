package com.spring.listener;

import com.spring.beanDefinitionRegistryPostProcessor.ContextLoadedAddBeanDefinitionRegistryPostProcessor;
import com.spring.context.event.MyApplicationContextLoadedEvent;
import org.springframework.context.ApplicationListener;

public class MyContextLoadedListener implements ApplicationListener<MyApplicationContextLoadedEvent> {
    @Override
    public void onApplicationEvent(MyApplicationContextLoadedEvent event) {
        event.getContext().addBeanFactoryPostProcessor(new ContextLoadedAddBeanDefinitionRegistryPostProcessor());
    }
}
