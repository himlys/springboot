package com.rain.springboot.rocketmq.accountTransfer;

public interface AccountTransferProducer {
    public void startAccountTransferTransaction(int userId,int money);
}
