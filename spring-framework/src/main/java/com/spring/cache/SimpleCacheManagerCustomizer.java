package com.spring.cache;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class SimpleCacheManagerCustomizer implements CacheManagerCustomizer {
    @Override
    public void customize(CacheManager cacheManager) {
        Collection c =  cacheManager.getCacheNames();
        System.out.println("c.size() = " + c.size());
    }
}
