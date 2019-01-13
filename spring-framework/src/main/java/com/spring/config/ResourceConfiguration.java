package com.spring.config;

import com.spring.annatation.UserServiceAnnatationImportAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations = {"classpath:/${my.placeholder.default}/spring-beans.xml"})
@UserServiceAnnatationImportAware(importAware = "userService annatation importaware ")
public class ResourceConfiguration {

}
