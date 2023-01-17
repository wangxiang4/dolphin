package com.cloud.dolphin.monitor.api.feign;

import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import com.cloud.dolphin.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *<p>
 * 远程Token api
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@FeignClient(contextId = "remoteTokenService", value = ServiceNameConstants.AUTH_SERVICE)
public interface RemoteTokenService {

	/**
	 * 分页查询token 信息
	 * @param params 分页参数
	 * @param from 内部调用标志
	 * @return page
	 */
	@PostMapping("/token/list")
	R getTokenPage(@RequestBody Map<String, Object> params, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 删除token
	 * @param token token
	 * @param from 调用标志
	 * @return
	 */
	@DeleteMapping("/token/{token}")
	R<Boolean> removeToken(@PathVariable("token") String token, @RequestHeader(SecurityConstants.FROM) String from);

}
