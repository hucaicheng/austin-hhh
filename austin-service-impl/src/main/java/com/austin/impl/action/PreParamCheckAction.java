package com.austin.impl.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.austin.common.constant.AustinConstant;
import com.austin.service.domain.MessageParam;
import com.austin.common.enums.RespStatusEnum;
import com.austin.impl.domain.SendTaskModel;
import com.austin.support.pipeline.BusinessProcess;
import com.austin.support.pipeline.ProcessContext;
import com.austin.common.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author hucaicheng
 * @Date 2023/7/18 0018 17:31
 * @Description: 前置参数校验
 */
@Slf4j
@Service
public class PreParamCheckAction implements BusinessProcess<SendTaskModel> {

    @Override
    public void process(ProcessContext<SendTaskModel> context) {

        SendTaskModel processModel = context.getProcessModel();
        Long messageTemplateId = processModel.getMessageTemplateId();
        List<MessageParam> messageParamList = processModel.getMessageParamList();
        // 没有传入 消息模板id和请求参数
        if (ObjectUtils.isEmpty(messageTemplateId) || CollUtil.isEmpty(messageParamList)){
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.CLIENT_BAD_PARAMETERS));
            return;
        }
        List<MessageParam> resultMessageParams = messageParamList.stream().
                filter(messageParam -> !StrUtil.isBlank(messageParam.getReceiver()))
                .collect(Collectors.toList());
        // 过滤没有接收者的消息
        if (CollUtil.isEmpty(resultMessageParams)){
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.CLIENT_BAD_PARAMETERS));
            return;
        }
        // 禁止接受人数超过100
        if (resultMessageParams.stream().anyMatch(messageParam -> messageParam.getReceiver().split(StrUtil.COMMA).length >
                AustinConstant.BATCH_RECEIVER_SIZE)){
            context.setNeedBreak(true).setResponse(BasicResultVO.fail(RespStatusEnum.TOO_MANY_RECEIVER));
            return;
        }

        processModel.setMessageParamList(resultMessageParams);
    }
}
