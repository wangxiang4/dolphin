package com.cloud.dolphin.auth.handler;

import com.cloud.dolphin.common.core.util.SpringContextHolderUtil;
import com.cloud.dolphin.common.log.event.SysLogEvent;
import com.cloud.dolphin.common.log.menus.LogTypeEnum;
import com.cloud.dolphin.common.log.util.SysLogUtils;
import com.cloud.dolphin.common.security.handler.AbstractAuthenticationFailureEventHandler;
import com.cloud.dolphin.monitor.api.entity.OperLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *<p>
 * 登录失败处理
 *</p>
 *
 * @Author: 开发团队-王翔
 * @Date: 2022/2/16
 */
@Slf4j
@Component
public class DolphinAuthenticationFailureEventHandler extends AbstractAuthenticationFailureEventHandler {

	/**
	 * 处理登录失败方法
	 * <p>
	 * @param authenticationException 登录的authentication 对象
	 * @param authentication 登录的authenticationException 对象
	 */
	@Override
	public void handle(AuthenticationException authenticationException, Authentication authentication) {
		Long startTime = System.currentTimeMillis();
		log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		OperLog operLog = SysLogUtils.getSysLog();
		operLog.setTitle("登录失败");
		operLog.setType(LogTypeEnum.ERROR.getType());
		operLog.setErrorMsg(authenticationException.getMessage());
		Long endTime = System.currentTimeMillis();
		operLog.setExecuteTime((endTime - startTime) + "毫秒");
		// 发送异步日志事件
		SpringContextHolderUtil.publishEvent(new SysLogEvent(operLog));
	}

}
