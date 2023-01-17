package com.cloud.dolphin.user.controller;

import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.user.entity.User;
import com.cloud.dolphin.user.feign.RemoteOrderService;
import com.cloud.dolphin.user.feign.RemotePointService;
import com.cloud.dolphin.user.service.UserService;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.spring.boot.autoconfigure.properties.client.ServiceProperties;
import io.seata.spring.boot.autoconfigure.properties.registry.RegistryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *<p>
 * 用户控制器
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/11
 */
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final RemoteOrderService remoteOrderService;
	private final RemotePointService remotePointService;

	private final RegistryProperties registryProperties;
	private final ServiceProperties serviceProperties;

	@GlobalTransactional(rollbackFor = Exception.class)
	@PostMapping("/user")
	public R createUser(@RequestBody User user) {
		System.out.println(registryProperties);
		System.out.println(serviceProperties);
		userService.saveOrUpdate(user);
		remotePointService.createPoint();
		remoteOrderService.createOrder();
		return R.ok();
	}

}
