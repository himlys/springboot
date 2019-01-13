package com.spring.data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
public class UserServiceEntity {
    int id;
    String name;

    public UserServiceEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
