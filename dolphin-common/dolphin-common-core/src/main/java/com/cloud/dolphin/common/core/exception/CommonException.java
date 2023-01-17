package com.cloud.dolphin.common.core.exception;

import lombok.NoArgsConstructor;

/**
 *<p>
 * 通用前端提示自定义异常信息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/18
 */
@NoArgsConstructor
public class CommonException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CommonException(String message) {
		super(message);
	}

	public CommonException(Throwable cause) {
		super(cause);
	}

	public CommonException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
