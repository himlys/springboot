package com.rain.springboot.business.impl;

import com.rain.springboot.business.BankAccountBusiness;
import com.rain.springboot.entity.BankAccount;
import com.rain.springboot.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountBusinessImpl implements BankAccountBusiness {
    @Autowired
    private BankAccountService bankAccountService;

    @Override
    public int transfer(int userId, int money) {
        BankAccount bankAccount = bankAccountService.selectById(userId);
        return bankAccountService.updateBankAccount(bankAccount.getId(), money);
    }
}
