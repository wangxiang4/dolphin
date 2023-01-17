package com.cloud.dolphin.common.security.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.CommonConstants;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import com.cloud.dolphin.common.data.entity.DolphinUser;
import com.cloud.dolphin.system.api.entity.User;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *<p>
 * 用户详细信息服务
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
public interface DolphinUserDetailsService extends UserDetailsService, Ordered {

	/**
	 * 是否支持此客户端校验
	 * @param clientId 目标客户端
	 * @return true/false
	 */
	default boolean support(String clientId, String grantType) {
		return true;
	}

	/**
	 * 排序值 默认取最大的
	 * @return 排序值
	 */
	@Override
	default int getOrder() {
		return 0;
	}

	/**
	 * 构建userDetails
	 * @param result 用户信息
	 * @return UserDetails
	 */
	default UserDetails getUserDetails(R<User> result) {
		if (result == null || result.getData() == null) {
			throw new UsernameNotFoundException("用户不存在");
		}

		User user = result.getData();
		Set<String> dbAuthsSet = new HashSet<>();

		if (ArrayUtil.isNotEmpty(user.getRoleIds())) {
			// 获取角色
			Arrays.stream(user.getRoleIds()).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));
			// 获取资源
			dbAuthsSet.addAll(Arrays.asList(user.getPermissions()));
		}

		Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));

		// 构造security用户
		return new DolphinUser(
				user.getId(),
				user.getDeptId(),
				user.getUserName(),
				SecurityConstants.BCRYPT + user.getPassword(),
				user.getPhone(),
				user.getTenantId(),
				true,
				true,
				true,
				StrUtil.equals(user.getStatus(), CommonConstants.STATUS_NORMAL),
				authorities
		);
	}

	/**
	 * 通过用户实体查询
	 * @param dolphinUser user
	 * @return
	 */
	default UserDetails loadUserByUser(DolphinUser dolphinUser) {
		return this.loadUserByUsername(dolphinUser.getUsername());
	}

}
