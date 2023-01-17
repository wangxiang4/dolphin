package com.cloud.dolphin.common.core.exception;

/**
 *<p>
 * 验证码异常
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/18
 */
public class ValidateCodeException extends RuntimeException {

	private static final long serialVersionUID = -7285211528095468156L;

	public ValidateCodeException() {
	}

	public ValidateCodeException(String msg) {
		super(msg);
	}

}
