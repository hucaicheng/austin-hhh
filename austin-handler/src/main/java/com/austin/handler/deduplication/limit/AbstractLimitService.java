package com.austin.handler.deduplication.limit;

import com.austin.common.domain.TaskInfo;
import com.austin.handler.deduplication.service.AbstractDeduplicationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @function
 * @Author hcc
 */
public abstract class AbstractLimitService implements LimitService{

    /**
     * 通过组装的入参获取其对应的所有key(一个接收者对应一个key)
     * @param service 去重器
     * @param taskInfo
     * @return
     */
    protected List<String> deduplicationSingleAllKey(AbstractDeduplicationService service, TaskInfo taskInfo){
        Set<String> receivers = taskInfo.getReceiver();
        List<String> result = new ArrayList<>(receivers.size());
        for (String receiver : receivers) {
            result.add(deduplicationSingleKey(service,receiver,taskInfo));
        }
        return result;
    }

    protected String deduplicationSingleKey(AbstractDeduplicationService abstractDeduplicationService, String receiver , TaskInfo taskInfo){
        return abstractDeduplicationService.deduplicationSingleKey(taskInfo,receiver);
    }
}
