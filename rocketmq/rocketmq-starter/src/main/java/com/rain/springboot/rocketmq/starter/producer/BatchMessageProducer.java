package com.rain.springboot.rocketmq.starter.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BatchMessageProducer implements InitializingBean {
    public void sendMessage() {
        DefaultMQProducer producer = new DefaultMQProducer("batch_producer");
        String topic = "batch_topic";
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "add_tag", "keys_tradeid_0", "37498143".getBytes()));
        messages.add(new Message(topic, "proc_tag", "keys_tradeid_1", "4873921".getBytes()));
        messages.add(new Message(topic, "p_tag", "keys_tradeid_2", "1897324".getBytes()));
        messages.add(new Message(topic, "a_tag", "keys_tradeid_3", "891743".getBytes()));
        try {
            producer.start();
            producer.send(messages);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        sendMessage();
    }
}
