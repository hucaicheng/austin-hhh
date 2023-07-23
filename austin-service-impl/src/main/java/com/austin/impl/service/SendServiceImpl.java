package com.austin.impl.service;

import cn.monitor4all.logRecord.annotation.OperationLog;
import com.austin.service.domain.BatchSendRequest;
import com.austin.service.domain.SendRequest;
import com.austin.service.domain.SendResponse;
import com.austin.common.enums.RespStatusEnum;
import com.austin.impl.domain.SendTaskModel;
import com.austin.service.service.SendService;
import com.austin.support.pipeline.ProcessContext;
import com.austin.support.pipeline.ProcessController;
import com.austin.support.pipeline.ProcessModel;
import com.austin.common.vo.BasicResultVO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @Author hucaicheng
 * @Date 2023/7/15 0015 10:48
 * @Description: TODO
 */
@Service
public class SendServiceImpl implements SendService {

    @Autowired
    private ProcessController processController;

    @Override
    @OperationLog(bizType = "SendService#send", bizId = "#sendRequest.messageTemplateId", msg = "#sendRequest")
    public SendResponse send(SendRequest sendRequest) {
        if(ObjectUtils.isEmpty(sendRequest)){
            return new SendResponse(RespStatusEnum.CLIENT_BAD_PARAMETERS);
        }
        // 创建发送消息任务模型
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(sendRequest.getMessageTemplateId())
                //Collections.singletonList :被限定只被分配一个内存空间，也就是只能存放一个元素的内容
                .messageParamList(Collections.singletonList(sendRequest.getMessageParam()))
                .build();
        //责任链上下文
        ProcessContext<ProcessModel> processContext = ProcessContext.builder()
                .processModel(sendTaskModel)
                .code(sendRequest.getCode())
                .needBreak(false)
                .response(BasicResultVO.success()).build();
        // 执行发送信息流程
        ProcessContext process = processController.process(processContext);
        return new SendResponse(process.getResponse().getStatus(),process.getResponse().getMsg());
    }

    @Override
    public SendResponse batchSend(BatchSendRequest batchSendRequest) {
        SendTaskModel sendTaskModel = SendTaskModel.builder()
                .messageTemplateId(batchSendRequest.getMessageTemplateId())
                .messageParamList(batchSendRequest.getMessageParamList())
                .build();
        //责任链上下文
        ProcessContext<ProcessModel> processContext = ProcessContext.builder()
                .processModel(sendTaskModel)
                .code(batchSendRequest.getCode())
                .needBreak(false)
                .response(BasicResultVO.success()).build();
        // 执行发送信息流程
        ProcessContext process = processController.process(processContext);
        return new SendResponse(process.getResponse().getStatus(),process.getResponse().getMsg());
    }
}
