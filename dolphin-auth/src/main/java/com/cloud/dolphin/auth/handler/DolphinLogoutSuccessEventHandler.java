package com.cloud.dolphin.auth.handler;

import com.cloud.dolphin.common.core.util.SpringContextHolderUtil;
import com.cloud.dolphin.common.core.util.WebUtil;
import com.cloud.dolphin.common.log.event.SysLogEvent;
import com.cloud.dolphin.common.log.util.SysLogUtils;
import com.cloud.dolphin.common.security.handler.AbstractLogoutSuccessEventHandler;
import com.cloud.dolphin.monitor.api.entity.OperLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

/**
 *<p>
 * 注销成功处理
 *</p>
 *
 * @Author: 开发团队-王翔
 * @Date: 2022/2/16
 */
@Slf4j
@Component
public class DolphinLogoutSuccessEventHandler extends AbstractLogoutSuccessEventHandler {

	/**
	 * 处理退出成功方法
	 * <p>
	 * 获取到登录的authentication 对象
	 * @param authentication 登录对象
	 */
	@Override
	public void handle(Authentication authentication) {
		Long startTime = System.currentTimeMillis();
		log.info("用户：{} 退出成功", authentication.getPrincipal());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		OperLog operLog = SysLogUtils.getSysLog();
		operLog.setTitle("退出成功");
		// 设置对应的token
		WebUtil.getRequest().ifPresent(request -> operLog.setOperParam(request.getHeader(HttpHeaders.AUTHORIZATION)));
		// 这边设置ServiceId
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
			operLog.setServiceId(auth2Authentication.getOAuth2Request().getClientId());
		}
		Long endTime = System.currentTimeMillis();
		operLog.setExecuteTime((endTime - startTime) + "毫秒");
		// 发送异步日志事件
		SpringContextHolderUtil.publishEvent(new SysLogEvent(operLog));
	}

}
