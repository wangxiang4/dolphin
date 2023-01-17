package com.cloud.dolphin.mq.controller;

import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.mq.service.TransactionOrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *<p>
 * 测试订单事务控制器
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/10
 */
@RestController
@AllArgsConstructor
public class TransactionOrderController {

    private final TransactionOrderService transactionOrderService;

    @GetMapping("/send/order")
    public R sendOrder() {
        transactionOrderService.testStreamTransaction();
        return R.ok("操作成功");
    }

}
