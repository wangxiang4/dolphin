package com.cloud.dolphin.system.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *<p>
 * 菜单枚举
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/18
 */
@Getter
@RequiredArgsConstructor
public enum MenuEnum {

	/**
	 * 菜单模块类(菜单目录)
	 */
	MENU_M("M", "菜单模块类(菜单目录)"),

	/**
	 * 菜单按钮
	 */
	MENU_F("F", "菜单按钮"),

	/**
	 * 菜单按钮
	 */
	MENU_C("C", "菜单"),

	/**
	 * 分割菜单重定向路由URL默认地址
	 */
	MENU_ROUTE_DEFAULT_URL("/dashboard/analysis", "分割菜单重定向路由URL默认地址"),

	/**
	 * 菜单0,启动,根菜单标识
	 */
	MENU_0("0", "启动,根菜单标识"),

	/**
	 * 菜单0,禁用
	 */
	MENU_1("1", "禁用"),

	/**
	 * 前端外链iframe组件
	 */
	COMPONENT_IFRAME("Iframe", "前端外链iframe组件"),

	/**
	 * 前端默认菜单目录根布局组件
	 */
	COMPONENT_LAYOUT("Layout", "前端默认多级菜单根布局组件"),

	/**
	 * 前端默认菜单多级目录根布局组件
	 */
	COMPONENT_PARENT_LAYOUT("ParentLayout", "前端默认菜单多级目录根布局组件");

	/**
	 * 值
	 */
	private final String value;

	/**
	 * 描述
	 */
	private final String description;

}
