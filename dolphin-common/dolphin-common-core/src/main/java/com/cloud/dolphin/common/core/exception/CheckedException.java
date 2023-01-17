package com.cloud.dolphin.common.core.exception;

import lombok.NoArgsConstructor;

/**
 *<p>
 * 检查异常
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/18
 */
@NoArgsConstructor
public class CheckedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CheckedException(String message) {
		super(message);
	}

	public CheckedException(Throwable cause) {
		super(cause);
	}

	public CheckedException(String message, Throwable cause) {
		super(message, cause);
	}

	public CheckedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
