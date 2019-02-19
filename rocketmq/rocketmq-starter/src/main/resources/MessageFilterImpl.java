package com.rain.springboot.rocketmq.starter.filter;

import org.apache.rocketmq.common.filter.FilterContext;
import org.apache.rocketmq.common.filter.MessageFilter;
import org.apache.rocketmq.common.message.MessageExt;

public class MessageFilterImpl implements MessageFilter {

    @Override
    public boolean match(MessageExt msg, FilterContext context) {
        return true;
    }
}