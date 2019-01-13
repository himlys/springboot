package com.spring.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionHandlerController1 {
    @RequestMapping("newexception")
    public JSONObject newexception() throws NewException {
        throw new NewException();
    }
    class NewException extends Throwable{

    }
}
