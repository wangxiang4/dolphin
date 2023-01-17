package com.cloud.dolphin.order.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dolphin.order.entity.Order;
import com.cloud.dolphin.order.mapper.OrderMapper;
import com.cloud.dolphin.order.service.OrderService;
import org.springframework.stereotype.Service;

/**
 *<p>
 * 订单业务实现类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/11
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
