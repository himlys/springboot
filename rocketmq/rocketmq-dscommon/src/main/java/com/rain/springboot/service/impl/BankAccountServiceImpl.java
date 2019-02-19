package com.rain.springboot.service.impl;

import com.rain.springboot.entity.BankAccount;
import com.rain.springboot.mapper.BankAccountMapper;
import com.rain.springboot.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountMapper bankAccountMapper;

    @Override
    public BankAccount selectById(int id) {
        return bankAccountMapper.selectById(id);
    }

    @Override
    public int updateBankAccount(int id, int money) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("money", money);
        return bankAccountMapper.updatePayAccount(map);
    }
}
