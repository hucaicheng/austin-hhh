package com.austin.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author hucaicheng
 * @Date 2023/7/11 0011 21:22
 * @Description: TODO
 */
@Getter
@ToString
@AllArgsConstructor
public enum BusinessCode {

    /**
     * 普通发送流程
     */
    COMMON_SEND("send","普通发送"),
    /**
     * 撤回流程
     */
    RECALL("recall","撤回消息");


    /**
     * code 关联着责任链的模板
     */
    private final String code;

    /**
     * 类型说明
     */
    private final String description;

}
