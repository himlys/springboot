package com.spring.properties;

import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class LocalPropertySourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        PropertySource<?> propertySource = (name != null ? new ResourcePropertySource(name, resource) : new ResourcePropertySource(resource));
        System.out.println("初始化自定义配置文件到env");
        return propertySource;
    }
}
