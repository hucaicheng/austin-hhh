package com.austin.support.pipeline;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.austin.common.enums.RespStatusEnum;
import com.austin.support.exception.ProcessException;
import com.austin.common.vo.BasicResultVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author hucaicheng
 * @Date 2023/7/18 0018 1:44
 * @Description: 流程控制器
 */

@Slf4j
@Data
public class ProcessController {

    /**
     * 模板映射
     */
    private Map<String, ProcessTemplate> templateConfig = null;

    public ProcessContext process(ProcessContext context){
        /**
         * 前置检查
         */
        try {
            preCheck(context);
        }catch (ProcessException e){
            return e.getProcessContext();
        }

        /**
         * 遍历流程节点
         */
        List<BusinessProcess> processList = templateConfig.get(context.getCode()).getProcessList();
        for (BusinessProcess businessProcess : processList) {
            businessProcess.process(context);
            if (context.getNeedBreak()){
                break;
            }
        }
        return context;
    }



    /**
     * 执行前检查，出错则抛出异常
     *
     * @param processContext 执行上下文
     * @throws ProcessException 异常信息
     */
    public void preCheck(ProcessContext processContext){
        // 上下文
        if (Objects.isNull(processContext)){
            processContext = new ProcessContext();
            processContext.setResponse(BasicResultVO.fail(RespStatusEnum.CONTEXT_IS_NULL));
            throw new ProcessException(processContext);
        }

        //业务代码
        String code = processContext.getCode();
        if (StrUtil.isBlank(code)){
            processContext.setResponse(BasicResultVO.fail(RespStatusEnum.BUSINESS_CODE_IS_NULL));
            throw new ProcessException(processContext);
        }

        // 执行模板
        ProcessTemplate processTemplate = templateConfig.get(code);
        if (Objects.isNull(processTemplate)){
            processContext.setResponse(BasicResultVO.fail(RespStatusEnum.PROCESS_TEMPLATE_IS_NULL));
            throw new ProcessException(processContext);
        }

        //执行模板列表
        List<BusinessProcess> processList = processTemplate.getProcessList();
        if (CollUtil.isEmpty(processList)){
            processContext.setResponse(BasicResultVO.fail(RespStatusEnum.PROCESS_LIST_IS_NULL));
            throw new ProcessException(processContext);
        }
    }
}
