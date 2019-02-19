package com.rain.springboot.rocketmq.accountTransfer;

import com.alibaba.fastjson.JSONObject;
import com.rain.common.base.utils.ObjectUtils;
import com.rain.springboot.business.PayAccountBusiness;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service("accountTransferTransactionListener")
public class AccountTransferTransactionListener implements TransactionListener {
    @Autowired
    private PayAccountBusiness payAccountBusiness;
    private ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        byte b[] = msg.getBody();
        JSONObject jsonObject = (JSONObject) ObjectUtils.bytesToObject(b).get();
        int userId = jsonObject.getInteger("userId");
        int money = jsonObject.getInteger("money");
        int i = payAccountBusiness.transfer(userId, money);
        if (i == 1) {
            localTrans.put(msg.getTransactionId(), 1);
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        localTrans.put(msg.getTransactionId(), 2);
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        Integer status = localTrans.get(msg.getTransactionId());
        if (null != status) {
            switch (status) {
                case 0:
                    return LocalTransactionState.UNKNOW;
                case 1:
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                default:
                    return LocalTransactionState.COMMIT_MESSAGE;
            }
        }
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
