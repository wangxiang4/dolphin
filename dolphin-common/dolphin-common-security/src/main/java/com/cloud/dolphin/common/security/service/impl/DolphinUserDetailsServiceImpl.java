package com.cloud.dolphin.common.security.service.impl;

import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import com.cloud.dolphin.common.security.service.DolphinUserDetailsService;
import com.cloud.dolphin.system.api.entity.User;
import com.cloud.dolphin.system.api.feign.RemoteUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *<p>
 * 用户详细信息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Slf4j
@Primary
@RequiredArgsConstructor
public class DolphinUserDetailsServiceImpl implements DolphinUserDetailsService {

	private final RemoteUserService remoteUserService;

	/**
	 * 用户名密码登录
	 * @param username 用户名
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		R<User> result = remoteUserService.selectByUserName(username, SecurityConstants.FROM_IN);
		UserDetails userDetails = getUserDetails(result);
		return userDetails;
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
