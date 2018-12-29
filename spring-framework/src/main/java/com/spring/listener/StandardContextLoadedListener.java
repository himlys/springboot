package com.spring.listener;

import com.spring.beanDefinitionRegistryPostProcessor.StandardAddBeanBefinitionRegistryPostProcessor;
import com.spring.beanFactoryPostProcessor.StandardAddBeanFactoryPostProcessor;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

public class StandardContextLoadedListener implements SmartApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationPreparedEvent) {
            onApplicationPreparedEvent((ApplicationPreparedEvent) event);
        }
    }

    private void onApplicationPreparedEvent(ApplicationPreparedEvent event) {
        event.getApplicationContext().addBeanFactoryPostProcessor(new StandardAddBeanBefinitionRegistryPostProcessor());
        event.getApplicationContext().addBeanFactoryPostProcessor(new StandardAddBeanFactoryPostProcessor());
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return true;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return true;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
