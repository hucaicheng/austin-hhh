package com.austin.support.mq.rocketmq;

import com.austin.support.constans.MessageQueuePipeline;
import com.austin.support.mq.SendMqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

/**
 * @Author hucaicheng
 * 作用
 */
@Service
@Slf4j
@ConditionalOnProperty(name = "austin.mq.pipeline" ,havingValue = MessageQueuePipeline.ROCKET_MQ)
public class RocketMqSendMqServiceImpl implements SendMqService {

    @Autowired
    private RocketMQTemplate rocketMqTemplate;

    @Override
    public void send(String topic, String jsonValue, String tagId) {
        if (StringUtils.isNotBlank(jsonValue)){
            topic = topic + ":" +tagId;
            send(topic, jsonValue);
        }
    }

    @Override
    public void send(String topic, String jsonValue) {
        rocketMqTemplate.send(topic, MessageBuilder.withPayload(jsonValue).build());
    }
}
