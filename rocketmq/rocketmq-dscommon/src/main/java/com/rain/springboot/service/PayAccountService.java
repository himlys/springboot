package com.rain.springboot.service;

import com.rain.springboot.entity.PayAccount;

public interface PayAccountService {
    public PayAccount selectById(int id);

    public int updatePayAccount(int id, int money);
}
