package com.cloud.dolphin.user.feign;

import com.cloud.dolphin.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *<p>
 * 远程调用积分接口
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/11
 */
@FeignClient(contextId = "remotePointService" , value = ServiceNameConstants.SEATA_POINT_SERVICE)
public interface RemotePointService {

	/** 创建积分 */
	@PostMapping("/point")
	void createPoint();

}
