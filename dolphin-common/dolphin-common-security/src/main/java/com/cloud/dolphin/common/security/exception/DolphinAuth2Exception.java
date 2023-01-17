package com.cloud.dolphin.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 *<p>
 * 自定义OAuth2Exception
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@JsonSerialize(using = DolphinAuth2ExceptionSerializer.class)
public class DolphinAuth2Exception extends OAuth2Exception {

	@Getter
	private String errorCode;

	public DolphinAuth2Exception(String msg) {
		super(msg);
	}

	public DolphinAuth2Exception(String msg, String errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}

}
