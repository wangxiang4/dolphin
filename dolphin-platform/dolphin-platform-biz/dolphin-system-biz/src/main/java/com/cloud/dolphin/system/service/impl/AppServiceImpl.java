package com.cloud.dolphin.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.javaer.aliyun.sms.SmsClient;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cloud.dolphin.system.api.entity.User;
import com.cloud.dolphin.system.service.AppService;
import com.cloud.dolphin.system.service.UserService;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.CacheConstants;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *<p>
 * 手机登录相关业务实现
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Slf4j
@Service
@AllArgsConstructor
public class AppServiceImpl implements AppService {

	private final RedisTemplate redisTemplate;

	private final UserService userService;

	private final SmsClient smsClient;

	/**
	 * 发送手机验证码
	 * 网关调用短信发送验证码返回前端
	 * @param phone 手机号
	 * @return code
	 */
	@Override
	public R<Boolean> sendSmsCode(String phone) {
		List<User> userList = userService.list(Wrappers.<User>query().lambda().eq(User::getPhone, phone));

		if (CollUtil.isEmpty(userList)) {
			log.info("手机号未注册:{}", phone);
			return R.ok(Boolean.FALSE, "手机号未注册");
		}

		Object codeObj = redisTemplate.opsForValue().get(CacheConstants.VERIFICATION_CODE + phone);

		if (codeObj != null) {
			log.info("手机号验证码未过期:{}，{}", phone, codeObj);
			return R.ok(Boolean.FALSE, "验证码发送过频繁");
		}

		String code = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.PHONE_CODE_SIZE));
		log.info("手机号生成验证码成功:{},{}", phone, code);
		redisTemplate.opsForValue().set(CacheConstants.VERIFICATION_CODE + phone, code, SecurityConstants.CODE_TIME,
				TimeUnit.SECONDS);

		// 调用短信通道发送
		this.smsClient.sendVerificationCode("ali-code",phone);
		return R.ok(Boolean.TRUE, code);
	}

}
