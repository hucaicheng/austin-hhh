package com.austin.handler.receiver.kafka;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.austin.common.domain.TaskInfo;
import com.austin.handler.receiver.utils.GroupIdMappingUtils;
import com.austin.support.constans.MessageQueuePipeline;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @Author hucaicheng
 * 消费kafka消息
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)//设置为多例
@ConditionalOnProperty(name = "austin.mq.pipeline" , havingValue = MessageQueuePipeline.KAFKA)
public class Receiver {

    @KafkaListener(topics = "#{'${austin.business.topic.name}'}",containerFactory = "filterContainerFactory")
    public void consumer(ConsumerRecord<?,String> consumerRecord, @Header(KafkaHeaders.GROUP_ID)String topicGroupId){
        Optional<String> kafkaMessage = Optional.ofNullable(consumerRecord.value());
        if (kafkaMessage.isPresent()){
            List<TaskInfo> taskInfos = JSON.parseArray(kafkaMessage.get(), TaskInfo.class);
            String groupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfos.iterator()));

            if (groupId.equals(topicGroupId)){
                System.out.printf(groupId);
            }
        }
    }
}
