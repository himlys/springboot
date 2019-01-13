package com.spring.data.jdbc.repositories;

import com.spring.data.entity.UserServiceEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserServiceRepository extends Repository<UserServiceEntity, Integer> {
    @Query("select * from userservice")
    public List<UserServiceEntity> getUserServiceEntities();
    @Modifying
    @Query("insert into userservice values(:id,:name)")
    public int save(@Param("id") int id,@Param("name") String name);
}
