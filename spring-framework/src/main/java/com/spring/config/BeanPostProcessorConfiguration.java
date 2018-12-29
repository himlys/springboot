package com.spring.config;

import com.spring.annatation.UserServiceAnnatationImportAware;
import com.spring.annatation.UserServiceComponentScan;
import com.spring.beanPostProcessor.UserServiceReferenceInstantiationAwareBeanPostProcessor;
import com.spring.service.UserServiceAware;
import com.spring.service.impl.UserServiceAwareImpl;
import com.spring.smartInstantiationAwareBeanPostProcessor.UserServiceSmartInstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import java.util.Map;

@Configuration
@Component
public class BeanPostProcessorConfiguration implements ImportAware {
    AnnotationMetadata importMetadata;
    private String importAware;
    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.importMetadata = importMetadata;
        Map<String, Object> map = importMetadata.getAnnotationAttributes(UserServiceAnnatationImportAware.class.getName());
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(map);
        importAware = annotationAttributes.getString("importAware");
    }
    @Bean
    public UserServiceAware userServiceAware(){
        return new UserServiceAwareImpl(importAware);
    }

    @Configuration
    @Import(UserServiceReferenceInstantiationAwareBeanPostProcessor.class)
    class ReferenceBeanPostProcessorConfiguration {

    }
    @Configuration
    @Import(UserServiceSmartInstantiationAwareBeanPostProcessor.class)
    class UserServiceSmartInstantiationAwareBeanPostProcessorConfiguration{

    }
}
