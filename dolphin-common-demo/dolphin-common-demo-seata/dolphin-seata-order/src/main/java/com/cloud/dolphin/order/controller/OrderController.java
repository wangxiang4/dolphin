package com.cloud.dolphin.order.controller;

import com.cloud.dolphin.order.entity.Order;
import com.cloud.dolphin.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *<p>
 * 订单控制器类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/11
 */
@RestController
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/order")
	public void createOrder() {
		Order order = new Order();
		int a = 1 / 0;
		order.setMoney(100);
		orderService.save(order);
	}
}
