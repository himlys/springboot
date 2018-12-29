package com.spring.runlistener;

import com.spring.context.event.MyApplicationContextLoadedEvent;
import com.spring.context.event.MyApplicationContextPreparedEvent;
import com.spring.context.event.MyApplicationStartingEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class MySpringApplicationRunListener implements SpringApplicationRunListener, Ordered {
    private final SpringApplication application;
    private final String[] args;
    private final ApplicationEventMulticaster applicationEventMulticaster;

    public MySpringApplicationRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
        this.applicationEventMulticaster = new MyApplicationEventMulticaster();
        for (ApplicationListener<?> listener : application.getListeners()) {
            this.applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    @Override
    public void starting() {
        this.applicationEventMulticaster.multicastEvent(new MyApplicationStartingEvent(this.application, this.args));
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {

    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        this.applicationEventMulticaster.multicastEvent(new MyApplicationContextPreparedEvent(this.application, this.args, context));
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        this.applicationEventMulticaster.multicastEvent(new MyApplicationContextLoadedEvent(this.application, this.args, context));
    }

    @Override
    public void started(ConfigurableApplicationContext context) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
