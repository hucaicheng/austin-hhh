package com.austin.handler.deduplication.limit;

import com.austin.common.domain.TaskInfo;
import com.austin.handler.deduplication.DeduplicationParam;
import com.austin.handler.deduplication.service.AbstractDeduplicationService;

import java.util.Set;

/**
 * @function 去重限制具体实现
 * @Author hcc
 */
public interface LimitService {

    /**
     * 去重实现
     * @param service  去重器对象
     * @param taskInfo
     * @param param 去重参数
     * @return 需要去重的接收者
     */
    Set<String> limitFilter(AbstractDeduplicationService service, TaskInfo taskInfo, DeduplicationParam param);

}
