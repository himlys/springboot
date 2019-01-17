package com.spring.redis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.spring.redis.service.RedisService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RedisServiceImpl implements RedisService, InitializingBean {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (redisTemplate == null) return;
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Set<Object> obj = redisTemplate.keys("USER_INFO_*");
        if (obj.size() > 0) {
            Map<String, JSONObject> m = new HashMap<>();
            for (Iterator iterator = obj.iterator(); iterator.hasNext(); ) {
                Object o = iterator.next();
                Object r = redisTemplate.opsForValue().get(o);
                if (r != null && r instanceof JSONObject) {
                    String staffId = ((JSONObject) r).getString("staffId");
                    String userName = ((JSONObject) r).getString("userName");
                    String result = staffId + "\t" + userName;
                    if (m.containsKey(staffId)) {
                        JSONObject j = m.get(staffId);
                        String id = ((JSONObject) j).getString("staffId");
                        String name = ((JSONObject) j).getString("userName");
                        String t = id + "\t" + name;
                        System.out.println("打印一个result = " + result + "\t" + t);
                    }
                }
            }
        }
    }
}
