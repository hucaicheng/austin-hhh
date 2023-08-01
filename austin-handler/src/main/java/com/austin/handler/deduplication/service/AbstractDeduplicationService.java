package com.austin.handler.deduplication.service;

import cn.hutool.core.collection.CollUtil;
import com.austin.common.domain.AnchorInfo;
import com.austin.common.domain.TaskInfo;
import com.austin.handler.deduplication.DeduplicationHolder;
import com.austin.handler.deduplication.DeduplicationParam;
import com.austin.handler.deduplication.limit.LimitService;

import com.austin.support.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 *  去重器
 * @function 当前抽象类存在的意义:
 *       1. 主要就是init()方法完成deduplicationHolder里面map的数据注入
 *       2. 由于两个子类组装参数的逻辑是一样的getParamsFromConfig 方法, 就抽出来放到抽象类中
 *       3. service是主要逻辑(去重), 也就是中间部分逻辑是不一样的, 如果去重全部写在子类(也就是子类实现去重接口DeduplicationService)
 *      那会出现重复代码, 而只要新增主要实现逻辑接口, 然后不同的去重逻辑写不同的实现类, 将当前类当作参数(LimitService), 然后让子类去得到对应的实现类注入到
 *      deduplicationHolder里面的map中, 就可以减少重复代码
 *
 *       写法总结: 当中间部分代码逻辑是一样的时候, 就将方法抽出来,调用, 就不用写重复代码(AbstractDeduplicationBuilder)
 *  *      而如果是开头和结尾代码一样, 中间逻辑不一样, 那就将新增一个中间逻辑实现类, 然后注入不同的中间类来完成, 就可以去除重复代码(AbstractDeduplicationService)
 * @Author hcc
 */
public abstract class AbstractDeduplicationService implements DeduplicationService{

    protected Integer deduplicationType;

    @Autowired
    private DeduplicationHolder deduplicationHolder;

    @Autowired
    private LogUtils logUtils;
    
    public LimitService limitService;

    @PostConstruct
    private void init(){
        deduplicationHolder.putService(deduplicationType,this);
    }

    public void deduplication(DeduplicationParam param){
        TaskInfo taskInfo = param.getTaskInfo();
        Set<String> filterReceiver = limitService.limitFilter(this, taskInfo, param);

        // 剔除符合去重条件的用户
        if (CollUtil.isNotEmpty(filterReceiver)){
            taskInfo.getReceiver().removeAll(filterReceiver);
            logUtils.print(AnchorInfo.builder().businessId(taskInfo.getBusinessId()).ids(filterReceiver).state(param.getAnchorState().getCode()).build());
        }
    }

    /**
     * 构建去重的Key
     *
     * @param taskInfo
     * @param receiver
     * @return
     */
    public abstract String deduplicationSingleKey(TaskInfo taskInfo, String receiver);
}
