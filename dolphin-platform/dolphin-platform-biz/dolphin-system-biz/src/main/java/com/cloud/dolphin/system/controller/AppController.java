package com.cloud.dolphin.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cloud.dolphin.system.api.entity.User;
import com.cloud.dolphin.system.service.AppService;
import com.cloud.dolphin.system.service.UserService;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.security.annotation.Inner;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *<p>
 * 移动端登录
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.APP_SYSTEM + "/app")
@Api(value = "app", tags = "手机管理模块")
public class AppController {

	private final AppService appService;

	private final UserService userService;

	@Inner(false)
	@GetMapping("/sendSmsCode/{phone:\\d+}")
	public R<Boolean> sendSmsCode(@PathVariable String phone) {
		return appService.sendSmsCode(phone);
	}

	/**
	 * 获取指定用户全部信息
	 * @param phone 手机号
	 * @return 用户信息
	 */
	@Inner
	@GetMapping("/selectByPhone/{phone:\\d+}")
	public R<User> selectByPhone(@PathVariable String phone) {
		User user = userService.getOne(Wrappers.<User>query().lambda().eq(User::getPhone, phone));
		if (user == null) {
			return R.error(String.format("您输入了无效的手机号,请联系管理员检查多租户下是否有当前手机号信息!", phone));
		}
		return R.ok(userService.getUserAuthority(user));
	}

}
