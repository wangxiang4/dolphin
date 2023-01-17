package com.cloud.dolphin.common.rocketmq.channel;

import com.cloud.dolphin.common.rocketmq.constant.MessageConstant;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 *<p>
 * 消费者Channel
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/9
 */
public interface DolphinSink {

	/**
	 * 短消息消费者
	 *
	 * @return SubscribableChannel
	 */
	@Input(MessageConstant.SMS_MESSAGE_INPUT)
	SubscribableChannel smsInput();


	/**
	 * 订单消费者
	 *
	 * @return SubscribableChannel
	 */
	@Input(MessageConstant.ORDER_MESSAGE_INPUT)
	SubscribableChannel orderInput();
}
