package com.rain.springboot.service.impl;

import com.rain.springboot.entity.PayAccount;
import com.rain.springboot.mapper.PayAccountMapper;
import com.rain.springboot.service.PayAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PayAccountServiceImpl implements PayAccountService {
    @Autowired
    private PayAccountMapper mapper;

    @Override
    public PayAccount selectById(int id) {
        return mapper.selectById(id);
    }

    @Override
    public int updatePayAccount(int id, int money) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("money", money);
        return mapper.updatePayAccount(map);
    }
}
