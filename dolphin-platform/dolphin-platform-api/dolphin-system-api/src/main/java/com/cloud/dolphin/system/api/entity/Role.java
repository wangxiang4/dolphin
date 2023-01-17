package com.cloud.dolphin.system.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.dolphin.common.data.entity.CommonEntity;
import com.cloud.dolphin.system.api.dto.CheckedInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *<p>
 * 角色信息表
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role")
public class Role extends CommonEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableId
    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色权限字符串
     */
    private String code;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /** 菜单组 */
    @TableField(exist = false)
    private CheckedInfo menuIds;

    /** 多租户组 */
    @TableField(exist = false)
    private String[] tenantIds;

}
