package com.austin.handler.pending;

import com.austin.handler.config.HandlerThreadPoolConfig;
import com.austin.handler.receiver.utils.GroupIdMappingUtils;
import com.austin.support.utils.ThreadPoolUtils;
import com.dtp.core.thread.DtpExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Author hucaicheng
 *
 * 储存 每种消息类型和taskPending 的关系
 */
@Component
public class TaskPendingHolder {

    @Autowired
    private ThreadPoolUtils threadPoolUtils;

    public Map<String, ExecutorService> taskPendingHolder = new HashMap<>(32);

    public static List<String> groupIds = GroupIdMappingUtils.getAllGroupIds();


    /**
     * 给每个渠道(groupId)，每种消息类型初始化一个线程池
     */
    @PostConstruct
    public void init(){
        for (String groupId : groupIds) {
            DtpExecutor executor = HandlerThreadPoolConfig.getExecutor(groupId);
            /**
             * example ThreadPoolName:austin.im.notice
             *
             * 可以通过apollo配置：dynamic-tp-apollo-dtp.yml  动态修改线程池的信息
             */
            threadPoolUtils.register(executor);
            taskPendingHolder.put(groupId,executor);
        }
    }

    /**
     * 通过groupId得到对应的线程池
     * @param groupId
     * @return
     */
    public ExecutorService route(String groupId){
        return taskPendingHolder.get(groupId);
    }
}
