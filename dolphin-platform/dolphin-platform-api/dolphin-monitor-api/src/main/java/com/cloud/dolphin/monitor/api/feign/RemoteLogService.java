package com.cloud.dolphin.monitor.api.feign;

import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import com.cloud.dolphin.common.core.constant.ServiceNameConstants;
import com.cloud.dolphin.monitor.api.entity.OperLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *<p>
 * 远程日志api
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.MONITOR_SERVICE)
public interface RemoteLogService {

	/**
	 * 保存日志
	 * @param operLog 日志实体
	 * @param from 内部调用标志
	 * @return R
	 */
	@PostMapping(AppConstants.APP_MONITOR + "/operLog/save")
	R<Boolean> saveLog(@RequestBody OperLog operLog, @RequestHeader(SecurityConstants.FROM) String from);

}
