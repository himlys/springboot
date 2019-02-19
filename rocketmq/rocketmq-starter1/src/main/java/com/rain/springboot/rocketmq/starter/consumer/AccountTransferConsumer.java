package com.rain.springboot.rocketmq.starter.consumer;

import com.alibaba.fastjson.JSONObject;
import com.rain.common.base.utils.ObjectUtils;
import com.rain.springboot.business.BankAccountBusiness;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountTransferConsumer implements InitializingBean {
    @Autowired
    private BankAccountBusiness bankAccountBusiness;
    public void initConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("account_transfer_consumer");
        try {
            consumer.subscribe("AccountTransferTopic", MessageSelector.bySql("(TAGS is not null and TAGS in ('transfer-money'))"));
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt messageExt : msgs) {
                        byte b[] = messageExt.getBody();
                        JSONObject jsonObject = (JSONObject) ObjectUtils.bytesToObject(b).get();
                        int userId = jsonObject.getInteger("userId");
                        Integer money = jsonObject.getInteger("money");

                        try {
                            int i = bankAccountBusiness.transfer(userId,money * -1);
                            if(i != 1){
                                throw new RuntimeException("不知道哪里错了，反正错了");
                            }
                        }catch (Exception e){
                            System.out.println("这里错了");
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }

                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initConsumer();
    }
}
