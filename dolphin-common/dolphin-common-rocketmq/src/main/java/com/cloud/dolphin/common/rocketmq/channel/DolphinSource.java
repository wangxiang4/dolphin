package com.cloud.dolphin.common.rocketmq.channel;

import com.cloud.dolphin.common.rocketmq.constant.MessageConstant;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 *<p>
 * 生产者Channel
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/9
 */
public interface DolphinSource {

	/**
	 * 短消息通道
	 *
	 * @return MessageChannel
	 */
	@Output(MessageConstant.SMS_MESSAGE_OUTPUT)
	MessageChannel smsOutput();

	/**
	 * 订单通道
	 *
	 * @return MessageChannel
	 */
	@Output(MessageConstant.ORDER_MESSAGE_OUTPUT)
	MessageChannel orderOutput();
}
