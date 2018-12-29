package com.spring.context.event;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

public class MyApplicationContextPreparedEvent extends MySpringApplicationEvent {
    private final ConfigurableApplicationContext context;

    public MyApplicationContextPreparedEvent(SpringApplication application, String[] args, ConfigurableApplicationContext context) {
        super(application, args);
        this.context = context;

    }

    public ConfigurableApplicationContext getContext() {
        return context;
    }
}
