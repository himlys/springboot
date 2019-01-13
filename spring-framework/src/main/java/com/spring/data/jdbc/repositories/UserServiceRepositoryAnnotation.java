package com.spring.data.jdbc.repositories;

import com.spring.data.entity.UserServiceEntityAnnotation;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = UserServiceEntityAnnotation.class, idClass = Integer.class)
public interface UserServiceRepositoryAnnotation {
    @Query("select * from user_service_entity")
    List<UserServiceEntityAnnotation> getUserServiceEntities();
}
