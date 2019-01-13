package com.spring.datasource.impl;

import com.spring.data.jdbc.repositories.UserServiceRepository;
import com.spring.data.jdbc.repositories.UserServiceRepositoryAnnotation;
import com.spring.datasource.UserServiceDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class UserServiceDataSourceImpl implements UserServiceDataSource, InitializingBean {
    @Autowired
    private DataSource dataSource;
    @Autowired
    UserServiceRepository userServiceRepository;
    @Autowired
    UserServiceRepositoryAnnotation userServiceRepositoryAnnotation;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(userServiceRepository.getUserServiceEntities());
        System.out.println(userServiceRepositoryAnnotation.getUserServiceEntities());
        System.out.println("dataSource " + dataSource.getConnection());
    }
}
