package com.cloud.dolphin.common.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *<p>
 * 基础模型
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2021/12/28
 */
@Data
public class BaseEntity implements Serializable {

    protected static final long serialVersionUID = 1L;

    /** 多租户ID */
    @ApiModelProperty("多租户ID")
    protected String tenantId;

    /** 当前用户 */
    @ApiModelProperty("当前用户")
    @TableField(exist = false)
    protected DolphinUser currentUser;

    /** 自定义sql过滤 */
    @TableField(exist = false)
    @JsonIgnore
    private String sqlFilter;

}
