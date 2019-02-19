package com.rain.springboot.rocketmq.starter.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Service
public class PullSQLMessageConsumer implements InitializingBean {
    private static final Map<MessageQueue, Long> OFFSE_TABLE = new HashMap<MessageQueue, Long>();

    public static void fetchMessage() {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("filter_sql_pull_consumer");
        try {
            consumer.start();
            Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues("filter_sql_topic");
            for (MessageQueue mq : mqs) {
                SINGLE_MQ:
                while (true) {
                    PullResult pullResult = consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
                    putMessageQueueOffset(mq, pullResult.getNextBeginOffset());
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            List<MessageExt> messageList = pullResult.getMsgFoundList();
                            for (MessageExt messageExt : messageList) {
                                byte b [] = messageExt.getBody();
                                System.out.println("messageExt = " + new String(b));
                            }
                            break;
                        case NO_NEW_MSG:
                            break SINGLE_MQ;
                        case NO_MATCHED_MSG:
                            break;
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                }
            }
            consumer.shutdown();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }
    }

    private static long getMessageQueueOffset(MessageQueue mq) {
        Long offset = OFFSE_TABLE.get(mq);
        if (offset != null)
            return offset;

        return 0;
    }

    private static void putMessageQueueOffset(MessageQueue mq, long offset) {
        OFFSE_TABLE.put(mq, offset);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        fetchMessage();
    }
}
