package com.cloud.dolphin.system.api.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 *<p>
 * 路由元信息
 * 路由设置属性
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Data
@Accessors(chain = true)
public class MetaVo implements Serializable {

    /**
     * 菜单名字
     */
    private String title;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否缓存该页面(true:是,false:不是)
     */
    private Boolean keepAlive;

    /**
     * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
     */
    private Boolean hideMenu;

    /**
     * 目录是否隐藏子菜单，当设置 true 的时候该目录下的子菜单不会再侧边栏出现
     */
    private Boolean hideChildrenInMenu;

    /**
     * Iframe内嵌显示地址
     */
    private String frameSrc;

    /**
     * 子路由
     */
    private List<MenuVo> children;

}
