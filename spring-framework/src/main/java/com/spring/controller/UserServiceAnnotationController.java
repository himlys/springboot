package com.spring.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.annatation.UserServiceReferenceAnnotation;
import com.spring.business.UserBusiness;
import com.spring.data.entity.UserServiceEntity;
import com.spring.service.UserServiceAnnotationService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Random;

@Controller
public class UserServiceAnnotationController implements InitializingBean {
    @UserServiceReferenceAnnotation
    private UserServiceAnnotationService userServiceAnnotationService;
    @Autowired
    private UserBusiness userBusiness;

    @Override
    public void afterPropertiesSet() throws Exception {
        String userName = userServiceAnnotationService.getUserName();
        System.out.println("userName = " + userName);
        System.out.println("userBusiness = " + userBusiness.getUserName());
    }

    @RequestMapping("/tx")
    public @ResponseBody JSONObject gtx() {
        UserServiceEntity entity = new UserServiceEntity(new Random().nextInt(), "leader");
        int i = userBusiness.insertUserService(entity);
        JSONObject obj = new JSONObject();
        obj.put("r", i);
        return obj;
    }
    @RequestMapping(value = "/tx/{1}",method = RequestMethod.GET)
    public @ResponseBody JSONObject gtxo(@PathVariable("1") String param, HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        obj.put("r", param);
        return obj;
    }
    @RequestMapping("/b.js")
    public @ResponseBody JSONObject b(@Param("1") String param) {
        JSONObject obj = new JSONObject();
        obj.put("r", param);
        return obj;
    }
}
