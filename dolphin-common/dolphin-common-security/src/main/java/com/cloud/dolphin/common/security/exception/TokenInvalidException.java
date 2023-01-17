package com.cloud.dolphin.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;

/**
 *<p>
 * 令牌不合法
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@JsonSerialize(using = DolphinAuth2ExceptionSerializer.class)
public class TokenInvalidException extends DolphinAuth2Exception {

	public TokenInvalidException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "invalid_token";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.FAILED_DEPENDENCY.value();
	}

}
