package com.cloud.dolphin.user.feign;

import com.cloud.dolphin.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *<p>
 * 远程调用订单接口
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/11
 */
@FeignClient(contextId = "remoteOrderService" , value = ServiceNameConstants.SEATA_ORDER_SERVICE)
public interface RemoteOrderService {

	/** 创建订单 */
	@PostMapping("/order")
	void createOrder();

}
