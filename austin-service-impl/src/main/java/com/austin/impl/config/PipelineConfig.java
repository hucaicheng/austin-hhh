package com.austin.impl.config;

import com.austin.service.enums.BusinessCode;
import com.austin.impl.action.AfterParamCheckAction;
import com.austin.impl.action.AssembleAction;
import com.austin.impl.action.PreParamCheckAction;
import com.austin.impl.action.SendMqAction;
import com.austin.support.pipeline.ProcessController;
import com.austin.support.pipeline.ProcessTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author hucaicheng
 * @Date 2023/7/18 0018 1:40
 * @Description: api层的pipeline配置类
 */
@Configuration
public class PipelineConfig {




    //1. 前置参数校验
    @Autowired
    private PreParamCheckAction preParamCheckAction;

    //2. 组装参数
    @Autowired
    private AssembleAction assembleAction;

    //3. 后置参数校验
    @Autowired
    private AfterParamCheckAction afterParamCheckAction;

    // 4. 发送mq
    @Autowired
    private SendMqAction sendMqAction;



    /**
     * pipeline流程控制器
     * 后续扩展则加BusinessCode和ProcessTemplate
     *
     * @return
     */
    @Bean
    public ProcessController processController(){
        ProcessController processController = new ProcessController();
        Map<String, ProcessTemplate> templateConfig = new HashMap<>(4);
        templateConfig.put(BusinessCode.COMMON_SEND.getCode(), commonSendTemplate());
        templateConfig.put(BusinessCode.RECALL.getCode(), recallMessageTemplate());
        processController.setTemplateConfig(templateConfig);
        return processController;
    }

    /**
     * 撤回消息流程
     * 1.组装参数
     * 2.发送mq
     * @return
     */
    @Bean("recallMessageTemplate")
    public ProcessTemplate recallMessageTemplate() {
        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessList(Arrays.asList(assembleAction,sendMqAction));
        return processTemplate;
    }

    /**
     * 普通发送执行流程
     * 1. 前置参数校验
     * 2. 组装参数 (通过入参的模板id去数据库找到对应模板, 再通过入参的可变参数完成模板信息的组装)
     * 3. 后置参数校验
     * 4. 发送消息至MQ
     *
     * @return
     */
    @Bean("commonSendTemplate")
    public ProcessTemplate commonSendTemplate() {
        ProcessTemplate processTemplate = new ProcessTemplate();
        processTemplate.setProcessList(Arrays.asList(preParamCheckAction,assembleAction,afterParamCheckAction,sendMqAction));
        return processTemplate;
    }
}
