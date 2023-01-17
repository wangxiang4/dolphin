package com.cloud.dolphin.auth.handler;

import com.cloud.dolphin.common.core.util.SpringContextHolderUtil;
import com.cloud.dolphin.common.log.event.SysLogEvent;
import com.cloud.dolphin.common.log.util.SysLogUtils;
import com.cloud.dolphin.common.security.handler.AbstractAuthenticationSuccessEventHandler;
import com.cloud.dolphin.monitor.api.entity.OperLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *<p>
 * 登录成功处理
 *</p>
 *
 * @Author: 开发团队-王翔
 * @Date: 2022/2/16
 */
@Slf4j
@Component
public class DolphinAuthenticationSuccessEventHandler extends AbstractAuthenticationSuccessEventHandler {

	/**
	 * 处理登录成功方法
	 * <p>
	 * 获取到登录的authentication 对象
	 * @param authentication 登录对象
	 */
	@Override
	public void handle(Authentication authentication) {
		Long startTime = System.currentTimeMillis();
		log.info("用户：{} 登录成功", authentication.getPrincipal());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		OperLog operLog = SysLogUtils.getSysLog();
		operLog.setTitle("登录成功");
		Long endTime = System.currentTimeMillis();
		operLog.setExecuteTime((endTime - startTime) + "毫秒");
		// 发送异步日志事件
		SpringContextHolderUtil.publishEvent(new SysLogEvent(operLog));
	}

}
