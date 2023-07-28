package com.austin.handler.receiver.service.impl;

import com.austin.common.domain.TaskInfo;
import com.austin.handler.receiver.service.ConsumeService;
import com.austin.support.domain.MessageTemplate;
import com.mysql.cj.util.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * @Author hucaicheng
 * 作用
 */
public class ConsumeServiceImpl implements ConsumeService {
    private static final String LOG_BIZ_TYPE = "Receiver#consumer";
    private static final String LOG_BIZ_RECALL_TYPE = "Receiver#recall";

    @Autowired
    private ApplicationContext context;
    @Autowired
    private LogUtils logUtils;

    @Override
    public void consume2Send(List<TaskInfo> taskInfoLists) {

    }

    @Override
    public void consume2recall(MessageTemplate messageTemplate) {

    }
}
