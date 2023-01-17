package com.cloud.dolphin.common.rocketmq.constant;

import com.cloud.dolphin.common.core.constant.StringPool;

/**
 *<p>
 * 消息中心常量
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/9
 */
public class MessageConstant {

	/**
	 * 生产者标识
	 */
	public static final String OUTPUT = "output";

	/**
	 * 消费者标识
	 */
	public static final String INPUT = "input";

	/**
	 * 短信消息
	 */
	public static final String SMS_MESSAGE = "sms";

	/**
	 * 订单消息
	 */
	public static final String ORDER_MESSAGE = "order";

	/**
	 * 消息生产者
	 */
	public static final String SMS_MESSAGE_OUTPUT = SMS_MESSAGE + StringPool.DASH + OUTPUT;


	/**
	 * 订单生产者
	 */
	public static final String ORDER_MESSAGE_OUTPUT = ORDER_MESSAGE + StringPool.DASH + OUTPUT;

	/**
	 * 短信消费者
	 */
	public static final String SMS_MESSAGE_INPUT = SMS_MESSAGE + StringPool.DASH + INPUT;

	/**
	 * 订单消费者
	 */
	public static final String ORDER_MESSAGE_INPUT = ORDER_MESSAGE + StringPool.DASH + INPUT;

	/**
	 * 订单组
	 */
	public static final String ORDER_BINDER_GROUP = ORDER_MESSAGE + StringPool.DASH + "binder-group";


}
