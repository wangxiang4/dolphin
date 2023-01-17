package com.cloud.dolphin.system.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cloud.dolphin.common.data.entity.TreeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *<p>
 * 菜单权限表
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
public class Menu extends TreeEntity<Menu> {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单类型（M模块 C菜单 F资源）
     */
    private String type;

    /**
     * 菜单地址
     */
    private String path;

    /**
     * 组件（前后端分离预留）
     */
    private String component;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否缓存该页面: 1:是  0:不是
     */
    private String keepAlive;

    /**
     * 是否隐藏(0显示 1隐藏)
     */
    private String hideMenu;

    /**
     * 是否隐藏子菜单(0显示 1隐藏)
     */
    private String hideChildrenMenu;

    /**
     * 新增时所需当前菜单的所有父级id
     * 进行修改角色模块的勾选类型,半选还是全选
     */
    @ApiModelProperty("所有父级id")
    @TableField(exist = false)
    private String[] parentIds;

}
