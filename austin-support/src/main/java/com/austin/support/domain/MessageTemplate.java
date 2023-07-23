package com.austin.support.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @description message_template
 * @author hcc
 * @date 2023-07-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Accessors(chain = true)
public class MessageTemplate {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题
     */
    private String name;

    /**
     * 当前消息审核状态： 10.待审核 20.审核成功 30.被拒绝
     */
    private int auditStatus;

    /**
     * 工单id
     */
    private String flowId;

    /**
     * 当前消息状态：10.新建 20.停用 30.启用 40.等待发送 50.发送中 60.发送成功 70.发送失败
     */
    private int msgStatus;

    /**
     * 定时任务id (xxl-job-admin返回)
     */
    private Long cronTaskId;

    /**
     * 定时发送人群的文件路径
     */
    private String cronCrowdPath;

    /**
     * 期望发送时间：0:立即发送 定时任务以及周期任务:cron表达式
     */
    private String expectPushTime;

    /**
     * 消息的发送id类型：10. userid 20.did 30.手机号 40.openid 50.email 60.企业微信userid
     */
    private int idType;

    /**
     * 消息发送渠道：10.im 20.push 30.短信 40.email 50.公众号 60.小程序 70.企业微信 80.钉钉机器人 90.钉钉工作通知 100.企业微信机器人 110.飞书机器人 110. 飞书应用消息
     */
    private int sendChannel;

    /**
     * 10.运营类 20.技术类接口调用
     */
    private int templateType;

    /**
     * 10.通知类消息 20.营销类消息 30.验证码类消息
     */
    private int msgType;

    /**
     * 10.夜间不屏蔽 20.夜间屏蔽 30.夜间屏蔽(次日早上9点发送)
     */
    private int shieldType;

    /**
     * 消息内容 占位符用{$var}表示
     */
    private String msgContent;

    /**
     * 发送账号 一个渠道下可存在多个账号
     */
    private int sendAccount;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 更新者
     */
    private String updator;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 业务方团队
     */
    private String team;

    /**
     * 业务方
     */
    private String proposer;

    /**
     * 是否删除：0.不删除 1.删除
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Integer created;

    /**
     * 更新时间
     */
    private Integer updated;

}
