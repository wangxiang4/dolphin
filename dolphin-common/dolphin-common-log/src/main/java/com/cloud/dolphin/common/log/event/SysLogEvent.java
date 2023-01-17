package com.cloud.dolphin.common.log.event;

import com.cloud.dolphin.monitor.api.entity.OperLog;
import org.springframework.context.ApplicationEvent;

/**
 *<p>
 * 系统日志事件
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
public class SysLogEvent extends ApplicationEvent {

	public SysLogEvent(OperLog source) {
		super(source);
	}

}
