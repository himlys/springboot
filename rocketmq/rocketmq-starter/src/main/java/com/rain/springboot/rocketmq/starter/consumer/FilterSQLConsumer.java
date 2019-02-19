package com.rain.springboot.rocketmq.starter.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FilterSQLConsumer implements InitializingBean {
    public void initFilterConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("filter_sql_consumer");
        try {
            consumer.subscribe("filter_sql_topic",MessageSelector.bySql("(TAGS is not null and TAGS in ('TagA', 'TagB'))" +
                    "and (a is not null and a between 0 and 3)"));
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for(MessageExt messageExt:msgs){
                        byte b[] = messageExt.getBody();
                        System.out.println("filter_sql_topic = " + new String(b));
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
        initFilterConsumer();
    }
}
