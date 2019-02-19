package com.rain.springboot.Controller;

import com.alibaba.fastjson.JSONObject;
import com.rain.springboot.rocketmq.accountTransfer.AccountTransferProducer;
import com.rain.springboot.rocketmq.starter.consumer.PullMessageConsumer;
import com.rain.springboot.rocketmq.starter.producer.StringProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StringProducerController {
    @Autowired
    private StringProducer stringProducer;
    @Autowired
    private AccountTransferProducer accountTransferProducer;

    @GetMapping("/push/{1}")
    public @ResponseBody
    JSONObject sendStringMessage(@PathVariable("1") String message) {
        JSONObject object = new JSONObject();
        object.put("0000", "OK");
        stringProducer.sendStringMessage(message);
        stringProducer.asyncSendStringMessage(message + "\t async");
        stringProducer.sendStringMessage("fetch_topic", "messageSend ");
        return object;
    }

    @GetMapping("/pull")
    public @ResponseBody
    JSONObject pullMessage() {
        JSONObject object = new JSONObject();
        PullMessageConsumer consumer = new PullMessageConsumer();
        consumer.fetchMessage();
        return object;
    }

    @GetMapping("/pulltask")
    public @ResponseBody
    JSONObject pullTaskMessage() {
        stringProducer.sendStringMessage("schedule_topic", "task message send ");
        return null;
    }

    @GetMapping("/pullfilter")
    public @ResponseBody
    JSONObject pullFilterMessage() {
        String topic = "filter_topic";
        DefaultMQProducer producer = new DefaultMQProducer("filter_class_producer_group");
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            String sendMessage = "task sql message send " + i;
            Message m = new Message();
            m.setTopic(topic);
            m.setBody(sendMessage.getBytes());
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
            m.putUserProperty("a", String.valueOf(div));
            messages.add(m);
        }
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
        } finally {
            producer.shutdown();
        }
        return null;
    }

    @GetMapping("/pullsqlfilter")
    public @ResponseBody
    JSONObject pullSQLFilterMessage() {
        String topic = "filter_sql_topic";
        DefaultMQProducer producer = new DefaultMQProducer("filter_tag_sql_producer_group");
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 120; i++) {
            String sendMessage = "task sql message send " + i;
            Message m = new Message();
            m.setTopic(topic);
            m.setBody(sendMessage.getBytes());
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
            m.putUserProperty("a", String.valueOf(div));
            messages.add(m);
        }
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
        } finally {
            producer.shutdown();
        }
        return null;
    }

    @GetMapping("/filtertag")
    public @ResponseBody
    JSONObject filterTag() {
        DefaultMQProducer producer = new DefaultMQProducer("filter_tag_producer_group");
        String topic = "filter_tag_topic";
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "TagA", "keys_tradeid_0", "37498143".getBytes()));
        try {
            producer.start();
            producer.send(messages);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
        return null;
    }

    @GetMapping("/accountTransfer/{1}/{2}")
    public @ResponseBody
    JSONObject accountTransfer(@PathVariable("1") int userId, @PathVariable("2") int money) {
        accountTransferProducer.startAccountTransferTransaction(userId, money * -1);
        return null;
    }
}
