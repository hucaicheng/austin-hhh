package com.austin.service.service;

import com.austin.service.domain.SendRequest;
import com.austin.service.domain.SendResponse;

/**
 * @Author hucaicheng
 * @Date 2023/7/11 0011 21:23
 * @Description: 撤回接口
 */

public interface RecallService {

    SendResponse recall(SendRequest sendRequest);
}
