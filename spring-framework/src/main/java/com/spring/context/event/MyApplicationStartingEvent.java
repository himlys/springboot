package com.spring.context.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.SpringApplicationEvent;

public class MyApplicationStartingEvent extends MySpringApplicationEvent {

    public MyApplicationStartingEvent(SpringApplication application, String[] args) {
        super(application, args);
    }
}
