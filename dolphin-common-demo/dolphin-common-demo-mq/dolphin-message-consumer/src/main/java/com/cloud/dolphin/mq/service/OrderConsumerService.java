package com.cloud.dolphin.mq.service;

import com.cloud.dolphin.common.rocketmq.constant.MessageConstant;
import com.cloud.dolphin.common.rocketmq.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *<p>
 * 消息订单消息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/9
 */
@Slf4j
@Service
public class OrderConsumerService {


	/**
	 * 消费分布式事务消息
	 * 注意,下游服务不能回滚事务,只要上游服务提交了事务,下游服务必须成功,如果下游服务失败了,就只能进行人工补偿
	 * 如果想要保证下游服务事务失败后可以回滚上游服务事务,这边也提供了解决方案,请参考Seata分布式事务实例
	 * @param order 　Order对象
	 */
	@StreamListener(MessageConstant.ORDER_MESSAGE_INPUT)
	@Transactional(rollbackFor = Exception.class)
	public void orderHandle(@Payload Order order) {
		log.error("接收到的订单消息为:{}", order);
	}

	/**
	 * 自定义全局异常处理
	 *
	 * @param message 消息体
	 */
	@StreamListener("errorChannel")
	public void error(Message<?> message) {
		ErrorMessage errorMessage = (ErrorMessage) message;
		log.error("Handling ERROR, errorMessage = {} ", errorMessage);
	}

}
