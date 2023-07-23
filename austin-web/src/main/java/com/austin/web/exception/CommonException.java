package com.austin.web.exception;

import com.austin.service.domain.SendResponse;
import com.austin.common.enums.RespStatusEnum;
import lombok.Getter;

/**
 * @Author hucaicheng
 * @Date 2023/7/14 0014 20:59
 * @Description: TODO
 */
@Getter
public class CommonException extends RuntimeException{

    private String code = RespStatusEnum.ERROR_400.getCode();
    private String msg = RespStatusEnum.ERROR_400.getMsg();

    public CommonException(String msg){
        super(msg);
    }

    public CommonException(RespStatusEnum respStatusEnum){
        super(respStatusEnum.getMsg());
        this.code= respStatusEnum.getCode();
        this.msg = respStatusEnum.getMsg();
    }

    public CommonException(SendResponse sendResponse){
        super(sendResponse.getMsg());
        this.code = sendResponse.getCode();
        this.msg = sendResponse.getMsg();
    }

    public CommonException(String code, String message, Exception e) {
        super(message, e);
        this.code = code;
    }
}
