package com.cloud.dolphin.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 *<p>
 * 无效异常
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@JsonSerialize(using = DolphinAuth2ExceptionSerializer.class)
public class InvalidException extends DolphinAuth2Exception {

	public InvalidException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "invalid_exception";
	}

	@Override
	public int getHttpErrorCode() {
		return 426;
	}

}
