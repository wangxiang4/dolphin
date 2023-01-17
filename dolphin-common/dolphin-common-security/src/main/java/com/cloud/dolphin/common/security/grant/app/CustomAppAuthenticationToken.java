package com.cloud.dolphin.common.security.grant.app;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *<p>
 * 手机验证码登录身份验证令牌
 * 授权类型:app
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
public class CustomAppAuthenticationToken extends AbstractAuthenticationToken {

	private final Object principal;

	/** 验证码/密码 */
	private String code;

	/**
	 * 授权类型
	 */
	@Getter
	private String grantType;

	public CustomAppAuthenticationToken(String phone, String code, String grantType) {
		super(AuthorityUtils.NO_AUTHORITIES);
		this.principal = phone;
		this.code = code;
		this.grantType = grantType;
	}

	public CustomAppAuthenticationToken(UserDetails sysUser) {
		super(sysUser.getAuthorities());
		this.principal = sysUser;
		// 设置认证成功 必须
		super.setAuthenticated(true);
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public Object getCredentials() {
		return this.code;
	}

}
