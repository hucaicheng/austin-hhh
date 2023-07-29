package com.austin.handler.pending;

import com.austin.common.domain.TaskInfo;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author hucaicheng
 *
 * Task 执行器
 * 0.丢弃消息
 * 2.屏蔽消息
 * 3.发送消息
 *
 */

@Data
@Component
@Slf4j
@Accessors(chain = true)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Task implements Runnable{

    private TaskInfo taskInfo;


    @Override
    public void run() {
        log.info("task-name :{}",Thread.currentThread().getName());
    }
}
