package com.cloud.dolphin.monitor.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.dolphin.common.data.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 *<p>
 * 操作日志记录
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "sys_oper_log", excludeProperty = { "remarks", "delFlag" })
public class OperLog extends CommonEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
    @TableId
    private String id;

    /**
     * 模块标题
     */
    private String title;

    /**
     * 日志类型
     */
    private String type;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 操作人员
     */
    private String operName;

    /**
     * 终端编号
     */
    private String clientId;

    /**
     * 请求URL
     */
    private String operUrl;

    /**
     * 主机地址
     */
    private String operIp;

    /**
     * 操作地点
     */
    private String operAddr;

    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 错误消息
     */
    private String executeTime;

    /**
     * 操作时间
     */
    private LocalDateTime operTime;

    /**
     * 服务器ID
     */
    private String serviceId;
}
