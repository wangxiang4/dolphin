package com.cloud.dolphin.common.log.event;

import com.cloud.dolphin.common.core.constant.SecurityConstants;
import com.cloud.dolphin.monitor.api.entity.OperLog;
import com.cloud.dolphin.monitor.api.feign.RemoteLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 *<p>
 * 异步监听日志事件
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Slf4j
@RequiredArgsConstructor
public class SysLogListener {

	private final RemoteLogService remoteLogService;

	@Async
	@Order
	@EventListener(SysLogEvent.class)
	public void saveSysLog(SysLogEvent event) {
		OperLog operLog = (OperLog) event.getSource();
		remoteLogService.saveLog(operLog, SecurityConstants.FROM_IN);
	}

}
