package com.cloud.dolphin.common.security.exp;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 *<p>
 * 接口权限判断
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
public class PermissionService {

	/**
	 * 判断接口是否有任意xxx，xxx权限
	 * @param permissions 权限
	 * @return {boolean}
	 */
	public boolean hasPermission(String... permissions) {
		if (ArrayUtil.isEmpty(permissions)) {
			return false;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		return authorities.stream().map(GrantedAuthority::getAuthority).filter(StringUtils::hasText)
				.anyMatch(x -> PatternMatchUtils.simpleMatch(permissions, x));
	}

	/**
	 * 验证用户是否具有以下任意一个权限
	 *
	 * @param permissions 以 ,为分隔符的权限列表
	 * @return 用户是否具有以下任意一个权限
	 */
	public boolean hasAnyPerm(String permissions) {
		if (StrUtil.isEmpty(permissions)) {
			return false;
		}
		for (String permission : permissions.split(",")) {
			if (permission != null && hasPermission(permission)) {
				return true;
			}
		}
		return false;
	}

}
