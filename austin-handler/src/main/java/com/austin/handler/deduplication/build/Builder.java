package com.austin.handler.deduplication.build;

import com.austin.common.domain.TaskInfo;
import com.austin.handler.deduplication.DeduplicationParam;

/**
 * @function 构建去重参数接口规范
 * @Author hcc
 */
public interface Builder {

    String DEDUPLICATION_CONFIG_PRE = "deduplication_";

    /**
     * 构建去重功能参数
     * @param deduplication 去重类型
     * @param taskInfo
     * @return
     */
    DeduplicationParam build(String deduplication, TaskInfo taskInfo);
}
