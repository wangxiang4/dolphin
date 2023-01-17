package com.cloud.dolphin.mq.service.impl;

import com.cloud.dolphin.common.rocketmq.channel.DolphinSource;
import com.cloud.dolphin.common.rocketmq.constant.MessageConstant;
import com.cloud.dolphin.common.rocketmq.entity.Order;
import com.cloud.dolphin.mq.service.TransactionOrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *<p>
 * 订单事务消息实现
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/9
 */
@Slf4j
@Service
@AllArgsConstructor
public class TransactionOrderServiceImpl implements TransactionOrderService {

	private final RocketMQTemplate rocketMQTemplate;

	private final DolphinSource dolphinSource;

	/**
	 * 这里消息发送只是half发送，
	 * 后面消息队列中half成功后，在TestTransactionListener中的executeLocalTransaction的方法中决定是否要提交本地事务
	 */
	@Override
	public void testTransaction() {

		Order order = new Order().setId(1L)
				.setGoodsId(100L)
				.setGoodsPrice(BigDecimal.valueOf(100.00))
				.setTradeId(100L)
				.setNumber(2)
				.setCreateTime(LocalDateTime.now());

		// 事务id
		String transactionId = UUID.randomUUID().toString();
		rocketMQTemplate.sendMessageInTransaction(MessageConstant.ORDER_BINDER_GROUP,
				MessageConstant.ORDER_MESSAGE_OUTPUT,
				MessageBuilder.withPayload(order)
						.setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
						.setHeader("share_id", 3).build(),
				4L);

		log.info("half消息发送成功");

	}

	@Override
	public void testStreamTransaction() {

		Order order = new Order().setId(1L)
				.setGoodsId(100L)
				.setGoodsPrice(BigDecimal.valueOf(100.00))
				.setTradeId(100L)
				.setNumber(2)
				.setCreateTime(LocalDateTime.now());
		// 事务id
		String transactionId = UUID.randomUUID().toString();
		dolphinSource.orderOutput().send(
				MessageBuilder.withPayload(order)
						.setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
						.setHeader("share_id", 3).build()
		);
		log.info("half消息发送成功");
	}
}
