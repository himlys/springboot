package com.rain.springboot.rocketmq.starter.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "batch_topic", consumerGroup = "batch_tag_consumer"
,selectorExpression = "add_tag")
public class TagConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("batch_topic add_tag = " + s);
    }
}
