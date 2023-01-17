package com.cloud.dolphin.common.log.menus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *<p>
 * 日志类型
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Getter
@RequiredArgsConstructor
public enum LogTypeEnum {

	/**
	 * 正常日志类型
	 */
	NORMAL("0", "正常日志"),

	/**
	 * 错误日志类型
	 */
	ERROR("9", "错误日志");

	/**
	 * 类型
	 */
	private final String type;

	/**
	 * 描述
	 */
	private final String description;

}
