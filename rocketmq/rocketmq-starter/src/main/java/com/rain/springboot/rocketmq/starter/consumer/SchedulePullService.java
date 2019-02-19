package com.rain.springboot.rocketmq.starter.consumer;

import org.apache.rocketmq.client.consumer.*;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class SchedulePullService implements InitializingBean {
    public void schedule() {
        final MQPullConsumerScheduleService scheduleService = new MQPullConsumerScheduleService("schedule_service");
        scheduleService.setMessageModel(MessageModel.CLUSTERING);
        scheduleService.registerPullTaskCallback("schedule_topic", new PullTaskCallback() {
            @Override
            public void doPullTask(MessageQueue mq, PullTaskContext context) {
                MQPullConsumer consumer = context.getPullConsumer();
                long offset = 0;
                try {
                    offset = consumer.fetchConsumeOffset(mq, false);
                    if (offset < 0)
                        offset = 0;

                    PullResult pullResult = consumer.pull(mq, "*", offset, 32);
                    switch (pullResult.getPullStatus()) {
                        case FOUND:
                            for (MessageExt messageExt : pullResult.getMsgFoundList()) {
                                byte[] b = messageExt.getBody();
                                System.out.println("task = " + new String(b));
                            }
                            break;
                        case NO_MATCHED_MSG:
                            break;
                        case NO_NEW_MSG:
                        case OFFSET_ILLEGAL:
                            break;
                        default:
                            break;
                    }
                    consumer.updateConsumeOffset(mq, pullResult.getNextBeginOffset());
                    context.setPullNextDelayTimeMillis(100);
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
        });
        try {
            scheduleService.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        schedule();
    }
}
