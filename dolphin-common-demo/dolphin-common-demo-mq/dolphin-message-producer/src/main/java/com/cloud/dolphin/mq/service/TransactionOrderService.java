package com.cloud.dolphin.mq.service;

/**
 *<p>
 * 订单事务消息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/9
 */
public interface TransactionOrderService {

	/**
	 * 测试事务消息,使用rocketMQ模板rocketMQTemplate发生
	 */
	void testTransaction();

	/**
	 * 通过spring-cloud-stream方式发送消息
	 * 注意配置spring.cloud.stream.rocketmq.bindings.order-output.producer.transactional=true
	 */
	void testStreamTransaction();
}
