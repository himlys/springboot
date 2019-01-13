package com.spring.data.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

public class UserServiceEntityAnnotation {
    int id;
    String name;

    public UserServiceEntityAnnotation(int id, String name) {
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
