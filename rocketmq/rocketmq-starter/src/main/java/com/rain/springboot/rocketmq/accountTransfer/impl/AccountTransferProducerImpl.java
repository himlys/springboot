package com.rain.springboot.rocketmq.accountTransfer.impl;

import com.alibaba.fastjson.JSONObject;
import com.rain.common.base.utils.ObjectUtils;
import com.rain.springboot.rocketmq.accountTransfer.AccountTransferProducer;
import com.rain.springboot.rocketmq.accountTransfer.AccountTransferTransactionListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class AccountTransferProducerImpl implements AccountTransferProducer {
    @Autowired
    private AccountTransferTransactionListener accountTransferTransactionListener;

    @Override
    public void startAccountTransferTransaction(int userId, int money) {
        TransactionMQProducer producer = new TransactionMQProducer("account_transfer_transaction_producer");
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });

        producer.setExecutorService(executorService);
        producer.setTransactionListener(accountTransferTransactionListener);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        Message msg = null;
        JSONObject object = new JSONObject();
        object.put("userId", userId);
        object.put("money", money);
        try {
            msg = new Message("AccountTransferTopic", "transfer-money", "KEY" + userId,
                    ObjectUtils.objectToBytes(object).get());
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);
            System.out.printf("%s%n", sendResult);
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }
}
