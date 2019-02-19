package com.rain.springboot.service;

import com.rain.springboot.entity.BankAccount;
import com.rain.springboot.entity.PayAccount;

public interface BankAccountService {
    public BankAccount selectById(int id);

    public int updateBankAccount(int id, int money);
}
