package com.rain.springboot.rocketmq.starter.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
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
public class FilterConsumer implements InitializingBean {
    public void initFilterConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("filter_consumer");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File classFile = new File(classLoader.getResource("MessageFilterImpl.java").getFile());
        String filterCode = null;
        try {
            filterCode = MixAll.file2String(classFile);
            consumer.subscribe("filter_topic", "com.rain.springboot.rocketmq.starter.filter.MessageFilterImpl",
                    filterCode);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt messageExt : msgs) {
                        byte b[] = messageExt.getBody();
                        System.out.println("filter_topic = " + new String(b));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initFilterConsumer();
    }
}
