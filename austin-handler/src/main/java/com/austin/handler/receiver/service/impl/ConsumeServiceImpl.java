package com.austin.handler.receiver.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.austin.common.domain.TaskInfo;
import com.austin.handler.pending.Task;
import com.austin.handler.pending.TaskPendingHolder;
import com.austin.handler.receiver.service.ConsumeService;
import com.austin.handler.receiver.utils.GroupIdMappingUtils;
import com.austin.support.domain.MessageTemplate;
import com.mysql.cj.util.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author hucaicheng
 * 作用
 */
@Service
public class ConsumeServiceImpl implements ConsumeService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private TaskPendingHolder taskPendingHolder;


    private static final String LOG_BIZ_TYPE = "Receiver#consumer";
    private static final String LOG_BIZ_RECALL_TYPE = "Receiver#recall";


    @Override
    public void consume2Send(List<TaskInfo> taskInfoLists) {
        String groupId = GroupIdMappingUtils.getGroupIdByTaskInfo(CollUtil.getFirst(taskInfoLists.iterator()));
        for (TaskInfo taskInfo : taskInfoLists) {
            Task task = context.getBean(Task.class).setTaskInfo(taskInfo);
            //创建对应的线程池去消费任务
            taskPendingHolder.route(groupId).execute(task);
        }

    }

    @Override
    public void consume2recall(MessageTemplate messageTemplate) {

    }
}
