package com.rain.springboot.rocketmq.starter.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "filter_tag_topic", consumerGroup = "filter_tag_consumer"
        ,selectorExpression = "TagA")
public class FilterTagConsumer implements RocketMQListener {

    @Override
    public void onMessage(Object o) {
        System.out.println("TagB TagA TagC add_tag = " + o);
    }
}