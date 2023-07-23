package com.austin.service.service;

import com.austin.service.domain.BatchSendRequest;
import com.austin.service.domain.SendRequest;
import com.austin.service.domain.SendResponse;

/**
 * @Author hucaicheng
 * @Date 2023/7/11 0011 21:23
 * @Description: 发送接口
 */
public interface SendService {

    /**
     * 单文案发送接口
     * @param sendRequest
     * @return
     */
    SendResponse send(SendRequest sendRequest);


    /**
     * 多文案发送接口
     * @param batchSendRequest
     * @return
     */
    SendResponse batchSend(BatchSendRequest batchSendRequest);


}
