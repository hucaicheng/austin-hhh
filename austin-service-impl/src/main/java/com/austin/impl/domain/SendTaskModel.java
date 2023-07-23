package com.austin.impl.domain;

import com.austin.service.domain.MessageParam;
import com.austin.common.domain.TaskInfo;
import com.austin.support.domain.MessageTemplate;
import com.austin.support.pipeline.ProcessModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author hucaicheng
 * @Date 2023/7/17 0017 19:58
 * @Description: 发送消息任务模型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendTaskModel implements ProcessModel {


    /**
     * 消息模板id
     */
    private Long messageTemplateId;

    /**
     * 请求参数
     */
    private List<MessageParam> messageParamList;

    /**
     * 发送任务的信息
     */
    private List<TaskInfo> taskInfos;

    /**
     * 撤回的任务的信息
     */
    private MessageTemplate messageTemplate;
}
