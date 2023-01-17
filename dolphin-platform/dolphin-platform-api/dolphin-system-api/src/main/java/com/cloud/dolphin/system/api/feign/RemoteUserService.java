package com.cloud.dolphin.system.api.feign;

import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import com.cloud.dolphin.common.core.constant.ServiceNameConstants;
import com.cloud.dolphin.system.api.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *<p>
 * 远程用户api
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteUserService {

	/**
	 * 通过用户名查询用户
	 * @param userName 用户名
	 * @param from 调用标志
	 * @return R
	 */
	@GetMapping(AppConstants.APP_SYSTEM + "/user/selectByUserName/{userName}")
	R<User> selectByUserName(@PathVariable("userName") String userName, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 通过手机号码查询用户
	 * @param phone 手机号码
	 * @param from 调用标志
	 * @return R
	 */
	@GetMapping(AppConstants.APP_SYSTEM +  "/user/selectByPhone/{phone}")
	R<User> selectByPhone(@PathVariable("phone") String phone, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 通过用户ID查询用户
	 * @param userid 用户id
	 * @param from 调用标志
	 * @return R
	 */
	@GetMapping(AppConstants.APP_SYSTEM + "/user/getUser/{userId}")
	R<User> selectByUserId(@PathVariable("userId") String userid, @RequestHeader(SecurityConstants.FROM) String from);

}
