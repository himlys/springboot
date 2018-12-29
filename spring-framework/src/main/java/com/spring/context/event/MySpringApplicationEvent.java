package com.spring.context.event;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationEvent;

public class MySpringApplicationEvent extends MyApplicationEvent {
    private final String[] args;

    /**
     * Create a new ApplicationEvent.
     *
     * @param application the object on which the event initially occurred (never {@code null})
     */
    public MySpringApplicationEvent(SpringApplication application, String[] args) {
        super(application);
        this.args = args;
    }
    public SpringApplication getSpringApplication() {
        return (SpringApplication) getSource();
    }
}
