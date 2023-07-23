package com.austin.web.controllor;

import com.alibaba.fastjson.JSON;
import com.austin.service.domain.MessageParam;
import com.austin.service.domain.SendRequest;
import com.austin.service.domain.SendResponse;
import com.austin.service.enums.BusinessCode;
import com.austin.common.enums.RespStatusEnum;
import com.austin.service.service.SendService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.austin.web.vo.MessageTemplateParam;
import com.austin.web.exception.CommonException;

import java.util.Map;
import java.util.Objects;

/**
 * @Author hucaicheng
 * @Date 2023/7/13 0013 10:46
 * @Description: TODO
 */
@Slf4j
@RestController
@RequestMapping("/messageTemplate")
@Api("发送消息")
public class MessageTemplateController {

    @Autowired
    private SendService sendService;

    @RequestMapping("/send")
    public SendResponse test(@RequestBody MessageTemplateParam messageTemplateParam){
        Map<String,String> variables = JSON.parseObject(messageTemplateParam.getMsgContent(),Map.class);
        //组装参数类型
        MessageParam messageParam = MessageParam.builder().receiver(messageTemplateParam.getReceiver()).variables(variables).build();
        SendRequest sendRequest = SendRequest.builder().code(BusinessCode.COMMON_SEND.getCode())
                .messageTemplateId(messageTemplateParam.getId()).messageParam(messageParam).build();
        SendResponse sendResponse = sendService.send(sendRequest);
        if (!Objects.equals(sendResponse.getCode(), RespStatusEnum.SUCCESS.getCode())){
            throw new CommonException(sendResponse);
        }
        return sendResponse;
    }


}
