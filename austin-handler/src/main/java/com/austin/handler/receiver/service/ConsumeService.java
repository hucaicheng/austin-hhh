package com.austin.handler.receiver.service;

import com.austin.common.domain.TaskInfo;
import com.austin.support.domain.MessageTemplate;

import java.util.List;

/**
 * @Author hucaicheng
 * 消费消息服务
 */
public interface ConsumeService {

    /**
     * 从MQ拉到消息进行消费，发送消息
     *
     * @param taskInfoLists
     */
    void consume2Send(List<TaskInfo> taskInfoLists);


    /**
     * 从MQ拉到消息进行消费，撤回消息
     *
     * @param messageTemplate
     */
    void consume2recall(MessageTemplate messageTemplate);
}
