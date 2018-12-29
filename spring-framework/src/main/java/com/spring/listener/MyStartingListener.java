package com.spring.listener;

import com.spring.context.event.MyApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class MyStartingListener implements ApplicationListener<MyApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(MyApplicationStartingEvent event) {
        System.out.println("启动 starting ");
    }
}
