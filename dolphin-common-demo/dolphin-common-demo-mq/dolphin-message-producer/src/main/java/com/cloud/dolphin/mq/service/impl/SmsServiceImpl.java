package com.cloud.dolphin.mq.service.impl;

import com.cloud.dolphin.common.rocketmq.channel.DolphinSource;
import com.cloud.dolphin.mq.service.SmsService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 *<p>
 * 发送短信实现类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/9
 */
@Service
@AllArgsConstructor
public class SmsServiceImpl implements SmsService {

	private final DolphinSource source;

	@Override
	public void sendSms(String message) {
		source.smsOutput().send(MessageBuilder.withPayload(message).build());
	}
}
