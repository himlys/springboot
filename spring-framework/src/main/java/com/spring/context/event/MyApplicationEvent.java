package com.spring.context.event;

import org.springframework.context.ApplicationEvent;

import java.util.EventObject;

public class MyApplicationEvent extends ApplicationEvent {
    private final long timestamp;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public MyApplicationEvent(Object source) {
        super(source);
        this.timestamp = System.currentTimeMillis();
    }
}
