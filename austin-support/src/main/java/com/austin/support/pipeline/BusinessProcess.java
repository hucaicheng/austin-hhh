package com.austin.support.pipeline;

/**
 * @Author hucaicheng
 * @Date 2023/7/18 0018 11:20
 * @Description: 业务执行器
 */
public interface BusinessProcess<T extends ProcessModel> {

    /**
     * 真正处理逻辑
     * @param context
     */
     void process(ProcessContext<T> context);
}
