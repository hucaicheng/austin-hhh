package com.austin.support.domain;

import lombok.Data;

import java.io.Serializable;
/**
 * @description sms_record
 * @author hcc
 * @date 2023-07-08
 */
@Data
public class SmsRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    private Long id;

    /**
    * 消息模板id
    */
    private Long messageTemplateId;

    /**
    * 手机号
    */
    private Long phone;

    /**
    * 发送短信渠道商的id
    */
    private int supplierId;

    /**
    * 发送短信渠道商的名称
    */
    private String supplierName;

    /**
    * 短信发送的内容
    */
    private String msgContent;

    /**
    * 下发批次的id
    */
    private String seriesId;

    /**
    * 计费条数
    */
    private int chargingNum;

    /**
    * 回执内容
    */
    private String reportContent;

    /**
    * 短信状态： 10.发送 20.成功 30.失败
    */
    private int status;

    /**
    * 发送日期：20211112
    */
    private Integer sendDate;

    /**
    * 创建时间
    */
    private Integer created;

    /**
    * 更新时间
    */
    private Integer updated;

    public SmsRecord() {}
}