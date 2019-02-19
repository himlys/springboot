package com.rain.springboot.rocketmq.starter.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "${demo.rocketmq.topic}", consumerGroup = "string_consumer")
public class StringConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        System.out.println("StringConsumer s = " + s);
    }
}
