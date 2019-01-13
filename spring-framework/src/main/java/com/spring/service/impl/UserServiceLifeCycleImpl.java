package com.spring.service.impl;

import com.spring.properties.AProperty;
import com.spring.service.UserServiceLifeCycle;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Service;

@Service
public class UserServiceLifeCycleImpl implements UserServiceLifeCycle, SmartLifecycle , InitializingBean {
    private boolean running = false;
    @Autowired
    private AProperty property;
    @Override
    public void start() {
        System.out.println("我启动了");
    }

    @Override
    public void stop() {
        System.out.println("我停了");
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {

    }

    @Override
    public int getPhase() {
        return 0;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AProperty.B b = property.getB();
        int c = b.getC();
        System.out.println("c = " + c);
    }
}
