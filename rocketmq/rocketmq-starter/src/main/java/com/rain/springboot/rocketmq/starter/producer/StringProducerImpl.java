package com.rain.springboot.rocketmq.starter.producer;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Service
public class StringProducerImpl implements StringProducer {
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Value("${demo.rocketmq.topic}")
    private String springTopic;

    public void sendStringMessage(String message) {
        SendResult sendResult = rocketMQTemplate.syncSend(springTopic, message);
    }

    public void sendMessage(Message message) {
        SendResult sendResult = rocketMQTemplate.syncSend(message.getTopic(), message);

    }

    public void asyncSendStringMessage(String message) {
        rocketMQTemplate.asyncSend(springTopic, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                String msgId = sendResult.getMsgId();
                System.out.println("msgId = " + msgId);
            }

            @Override
            public void onException(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void sendStringMessage(String topic, String message) {
        SendResult sendResult = rocketMQTemplate.syncSend(topic, message);
    }

    @Override
    public void sendSQLFilterMessage(String topic, String message) {
        Message m = new Message();
        m.setBody(message.getBytes());
        Random random = new Random();
        int i = random.nextInt(20);
        String tag;
        int div = i % 3;
        if (div == 0) {
            tag = "TagA";
        } else if (div == 1) {
            tag = "TagB";
        } else {
            tag = "TagC";
        }
        m.setTags(tag);
        m.putUserProperty("a", String.valueOf(i));
        SendResult sendResult = rocketMQTemplate.syncSend(topic, m);
        String msgId = sendResult.getMsgId();
        System.out.println("msgId = " + msgId);
    }
}
