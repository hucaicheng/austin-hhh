package com.austin.handler.deduplication.service;

import com.austin.handler.deduplication.DeduplicationParam;

/**
 * @function 去重功能接口
 * @Author hcc
 */
public interface DeduplicationService {

    /**
     * 消息去重
     * @param deduplicationParam
     */
    void deduplication(DeduplicationParam deduplicationParam);
}
