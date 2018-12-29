package com.spring.config;

import com.spring.properties.AProperty;
import com.spring.properties.LocalPropertySourceFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(AProperty.class)
@PropertySource(value = "classpath:/${my.placeholder.default}/a.properties", factory = LocalPropertySourceFactory.class)
public class PropertiesConfiguration {
}
