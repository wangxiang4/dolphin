package com.cloud.dolphin.mq.service;

import com.cloud.dolphin.common.rocketmq.constant.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 *<p>
 * 短信消费者业务
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/9
 */
@Slf4j
@Service
public class SmsConsumerService {


	/**
	 * 监控听短信input
	 * @param message 消费文本内容
	 */
	@StreamListener(MessageConstant.SMS_MESSAGE_INPUT)
	public void handler(@Payload String message) {
		log.error("接收到的消息为:{}", message);
	}


}
