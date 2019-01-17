package com.spring.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EntityController implements InitializingBean {

    @RequestMapping("entity")
    public @ResponseBody
    NewUser getUser(@RequestBody User user) {
        System.out.println("entity = " + JSON.toJSONString(user));
        NewUser newUser = new NewUser(123, "newUser", 28);
        return newUser;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    static class NewUser {
        int id;
        String name;
        int age;

        public NewUser() {
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

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public NewUser(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }

    static class User {
        int id;
        String name;

        public User() {
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

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}

