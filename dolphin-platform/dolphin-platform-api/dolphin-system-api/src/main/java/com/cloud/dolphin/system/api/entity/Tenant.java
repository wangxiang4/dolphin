package com.cloud.dolphin.system.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.dolphin.common.data.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 *<p>
 * 多租户实体类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/5/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "sys_tenant", excludeProperty = { "tenantId" })
public class Tenant  extends CommonEntity {

    /**
     * 租户ID
     */
    @TableId
    private String id;

    /**
     * 租户名称
     */
    private String name;

    /**
     * 租户编码
     */
    private String code;

    /**
     * 租户开始时间
     */
    private LocalDateTime tenantStartTime;

    /**
     * 租户结束时间
     */
    private LocalDateTime tenantEndTime;

    /**
     * 租户状态
     */
    private String status;

    /**
     * 查询条件,多租户ids
     */
    @TableField(exist = false)
    private String[] tenantIds;

}
