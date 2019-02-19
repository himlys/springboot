package com.rain.springboot.rocketmq.starter.producer;

import org.apache.rocketmq.common.message.Message;

public interface StringProducer {
    public void sendStringMessage(String message);

    public void asyncSendStringMessage(String message);

    public void sendStringMessage(String topic, String message);

    public void sendSQLFilterMessage(String topic, String message);

    public void sendMessage(Message message);
}
