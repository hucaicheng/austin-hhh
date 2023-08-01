package com.austin.handler.deduplication.service;

import cn.hutool.core.util.StrUtil;
import com.austin.common.domain.TaskInfo;
import com.austin.common.enums.AnchorState;
import com.austin.common.enums.DeduplicationType;
import com.austin.handler.deduplication.limit.LimitService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @function 频次去重服务
 * @Author hcc
 */
@Service
public class FrequencyDeduplicationService extends AbstractDeduplicationService{

    public FrequencyDeduplicationService(@Qualifier("SimpleLimitService") LimitService limitService){
        this.deduplicationType = AnchorState.RULE_DEDUPLICATION.getCode();
        this.limitService = limitService;
    }

    private static final String PREFIX = "FRE";

    /**
     * 业务规则去重 构建key
     * <p>
     * key ： receiver + templateId + sendChannel
     * <p>
     * 一天内一个用户只能收到某个渠道的消息 N 次
     *
     * @param taskInfo
     * @param receiver
     * @return
     */
    @Override
    public String deduplicationSingleKey(TaskInfo taskInfo, String receiver) {
        return PREFIX + StrUtil.C_UNDERLINE
                + receiver + taskInfo.getMessageTemplateId() + taskInfo.getSendChannel();
    }
}
