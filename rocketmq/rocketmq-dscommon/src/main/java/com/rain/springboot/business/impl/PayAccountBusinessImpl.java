package com.rain.springboot.business.impl;

import com.asiainfo.spring.common.logging.logback.BaseLoggingObject;
import com.rain.springboot.business.PayAccountBusiness;
import com.rain.springboot.entity.PayAccount;
import com.rain.springboot.service.PayAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayAccountBusinessImpl extends BaseLoggingObject implements PayAccountBusiness {
    @Autowired
    private PayAccountService payAccountService;

    @Transactional
    public int transfer(int userId, int money) {
        PayAccount payAccount = payAccountService.selectById(userId);
        return payAccountService.updatePayAccount(payAccount.getId(), money);
    }
}
