package com.spring.cache;

import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Callable;

@Configuration
public class SimpleCacheManagerConfiguration {
    @Bean
    public Cache cache(){
        return new Cache() {
            @Override
            public String getName() {
                return "userServiceSimpleCache";
            }

            @Override
            public Object getNativeCache() {
                return null;
            }

            @Override
            public ValueWrapper get(Object key) {
                return null;
            }

            @Override
            public <T> T get(Object key, Class<T> type) {
                return null;
            }

            @Override
            public <T> T get(Object key, Callable<T> valueLoader) {
                return null;
            }

            @Override
            public void put(Object key, Object value) {

            }

            @Override
            public ValueWrapper putIfAbsent(Object key, Object value) {
                return null;
            }

            @Override
            public void evict(Object key) {

            }

            @Override
            public void clear() {

            }
        };
    }
}
