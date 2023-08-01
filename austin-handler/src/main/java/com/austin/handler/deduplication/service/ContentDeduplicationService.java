package com.austin.handler.deduplication.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.austin.common.domain.TaskInfo;
import com.austin.common.enums.AnchorState;
import com.austin.handler.deduplication.limit.LimitService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * @function 内容去重(5分钟以内相同的内容发送给相同的用户)服务
 * @Author hcc
 */
@Service
public class ContentDeduplicationService extends AbstractDeduplicationService {

    /**
     *
     * @param limitService 不同的去重逻辑, 就注入不同的LimitService实现类
     */
    public ContentDeduplicationService(@Qualifier("SlideWindowLimitService")LimitService limitService){
        this.deduplicationType = AnchorState.CONTENT_DEDUPLICATION.getCode();
        this.limitService = limitService;
    }

    /**
     * 内容去重 构建key
     * <p>
     * key: md5(templateId + receiver + content)
     * <p>
     * 相同的内容相同的模板短时间内发给同一个人
     *
     * @param taskInfo
     * @return
     */
    @Override
    public String deduplicationSingleKey(TaskInfo taskInfo, String receiver) {
        return DigestUtil.md5Hex(taskInfo.getMessageTemplateId() + receiver + JSON.toJSONString(taskInfo.getContentModel()));
    }
}
