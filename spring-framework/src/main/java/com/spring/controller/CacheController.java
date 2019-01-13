package com.spring.controller;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.stereotype.Controller;

@Controller
public class CacheController implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
