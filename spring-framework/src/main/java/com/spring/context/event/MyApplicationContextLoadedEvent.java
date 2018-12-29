package com.spring.context.event;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class MyApplicationContextLoadedEvent extends MyApplicationStartingEvent {
    private final ConfigurableApplicationContext context;

    public ConfigurableApplicationContext getContext() {
        return context;
    }

    public MyApplicationContextLoadedEvent(SpringApplication application, String[] args, ConfigurableApplicationContext context) {
        super(application, args);
        this.context = context;
    }
}
