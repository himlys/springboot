package com.spring.business;

import com.spring.data.entity.UserServiceEntity;

public interface UserBusiness {
    String getUserName();
    int insertUserService(UserServiceEntity entity);
}
