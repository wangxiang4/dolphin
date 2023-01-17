package com.cloud.dolphin.common.security.service.impl;

import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import com.cloud.dolphin.common.data.entity.DolphinUser;
import com.cloud.dolphin.common.security.service.DolphinUserDetailsService;
import com.cloud.dolphin.system.api.entity.User;
import com.cloud.dolphin.system.api.feign.RemoteUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class DolphinAppUserDetailsServiceImpl implements DolphinUserDetailsService {

	private final RemoteUserService remoteUserService;

	/**
	 * 手机号登录
	 * @param phone 手机号
	 * @return
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String phone) {
		R<User> result = remoteUserService.selectByPhone(phone, SecurityConstants.FROM_IN);
		UserDetails userDetails = getUserDetails(result);
		return userDetails;
	}

	/**
	 * check-token 使用
	 * @param dolphinUser user
	 * @return
	 */
	@Override
	public UserDetails loadUserByUser(DolphinUser dolphinUser) {
		return this.loadUserByUsername(dolphinUser.getPhone());
	}

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	@Override
	public boolean support(String clientId, String grantType) {
		return SecurityConstants.APP.equals(grantType);
	}

}
