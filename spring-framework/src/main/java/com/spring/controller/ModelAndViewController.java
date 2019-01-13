package com.spring.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ModelAndViewController {
    @RequestMapping("/mv/{1}")
    public ModelAndView gtxo(@PathVariable("1") String param) {
        ModelAndView mv = new ModelAndView();
        if(!"default".equals(param)){
            mv.setViewName("/" + param);
        }
        return mv;
    }
}
