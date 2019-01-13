package com.spring.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.exception.UserServiceException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExceptionHandlerController {
    @RequestMapping("runtimeexception")
    public JSONObject runtimeException() {
        throw new RuntimeException("run time exception");
    }

    @RequestMapping("arithmeticException")
    public JSONObject exception() {
        int i = 1 / 0;
        return null;
    }
    @RequestMapping(value = "methodNotSupported",method = RequestMethod.POST)
    public JSONObject abc() {
        int i = 1 / 0;
        return null;
    }
    @RequestMapping("userserviceexception")
    public JSONObject e() {
        throw new UserServiceException();
    }
}
