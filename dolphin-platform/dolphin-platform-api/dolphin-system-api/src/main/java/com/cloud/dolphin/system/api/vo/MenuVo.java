package com.cloud.dolphin.system.api.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *<p>
 * 菜单路由信息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Data
public class MenuVo implements Serializable {

    /**
     * 路由名字
     */
    private String name;

    /**
     * 分割菜单重定向URL
     */
    private String redirect;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 设置属性
     */
    private MetaVo meta;

    /**
     * 子路由
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MenuVo> children;

}

