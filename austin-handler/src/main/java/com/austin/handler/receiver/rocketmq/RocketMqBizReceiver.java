package com.austin.handler.receiver.rocketmq;

import com.austin.support.constans.MessageQueuePipeline;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @Author hucaicheng
 * 消费rocketMq消息
 */

@Slf4j
@Component
/**
 * @ConditionalOnProperty 注解作用: 当满足特定条件, 当前bean才会放入spring容器中
 *
 *  name: application.properties 文件中必须配置了对应属性的值
 *  prefix: name属性的前缀
 *  havingValue: application.properties配置配置的name的值必须和当前值相等才加载
 */
@ConditionalOnProperty(name = "austin.mq.pipeline", havingValue = MessageQueuePipeline.ROCKET_MQ)
@RocketMQMessageListener(topic = "${austin.business.topic.name}", // 主题: 指该消费者组所订阅的消息服务
        /**
         * 概念：消费者组（多个消费者） 此参数相同即为同一个消费者组
         * 作用: 集群模式负载均衡的实现，广播模式的通知的实现
        */
        consumerGroup = "${austin.rocketmq.biz.consumer.group}",
                        selectorType = SelectorType.TAG,
                        selectorExpression = "${austin.business.tagId.value}" )//控制可以选择哪个消息

public class RocketMqBizReceiver implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("message: "+message);
    }
}
