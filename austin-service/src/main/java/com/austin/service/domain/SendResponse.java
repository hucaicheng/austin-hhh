package com.austin.service.domain;

import com.austin.common.enums.RespStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * 发送接口返回值
 *
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class SendResponse {
    /**
     * 响应状态
     */
    private String code;

    /**
     * 响应编码
     */
    private String msg;

    public SendResponse(RespStatusEnum clientBadParameters) {
        this.code = clientBadParameters.getCode();
        this.msg = clientBadParameters.getMsg();
    }
}
