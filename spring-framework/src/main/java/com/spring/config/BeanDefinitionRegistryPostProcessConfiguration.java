package com.spring.config;

import com.spring.beanDefinitionRegistryPostProcessor.ConfigurationBeanDefinitionRegistryPostProcessor;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanDefinitionRegistryPostProcessConfiguration {
    @Bean
    public static ConfigurationBeanDefinitionRegistryPostProcessor configurationBeanDefinitionRegistryPostProcessor() {
        return new ConfigurationBeanDefinitionRegistryPostProcessor();
    }

    @Bean
    public JettyServletWebServerFactory JettyServletWebServerFactory() {
        return new JettyServletWebServerFactory();
    }
}
