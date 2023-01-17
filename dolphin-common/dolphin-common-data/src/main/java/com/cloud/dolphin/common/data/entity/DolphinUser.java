package com.cloud.dolphin.common.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 *<p>
 * 扩展安全框架用户信息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class DolphinUser extends User {

	/**
	 * 用户ID
	 */
	@Getter
	private String id;

	/**
	 * 部门ID
	 */
	@Getter
	private String deptId;

	/**
	 * 手机号
	 */
	@Getter
	private String phone;

	/**
	 * 多租户ID
	 */
	@Getter
	@Setter
	private String tenantId;

	public DolphinUser(String id, String deptId, String username, String password, String phone, String tenantId, boolean enabled,
					   boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
					   Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.id = id;
		this.deptId = deptId;
		this.phone = phone;
		this.tenantId = tenantId;
	}

}
