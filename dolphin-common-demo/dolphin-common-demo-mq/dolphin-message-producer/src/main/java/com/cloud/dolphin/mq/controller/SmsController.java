package com.cloud.dolphin.mq.controller;

import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.mq.service.SmsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *<p>
 * 发送短信控制器
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/9
 */
@RestController
@AllArgsConstructor
public class SmsController {

	private final SmsService smsService;

	@GetMapping("/send/sms")
	public R sendSms(String message) {
		smsService.sendSms(message);
		return R.ok("操作成功");
	}

}
