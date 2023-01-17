package com.cloud.dolphin.system.service;

import com.cloud.dolphin.common.core.api.R;

/**
 *<p>
 * 移动端 服务类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
public interface AppService {

	/**
	 * 发送手机验证码
	 * @param mobile mobile
	 * @return code
	 */
	R<Boolean> sendSmsCode(String mobile);

}
