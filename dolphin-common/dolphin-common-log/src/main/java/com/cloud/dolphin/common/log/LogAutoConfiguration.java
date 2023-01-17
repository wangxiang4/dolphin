package com.cloud.dolphin.common.log;

import com.cloud.dolphin.common.log.aspect.SysLogAspect;
import com.cloud.dolphin.common.log.event.SysLogListener;
import com.cloud.dolphin.monitor.api.feign.RemoteLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 *<p>
 * 日志自动配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@EnableAsync
@RequiredArgsConstructor
@ConditionalOnWebApplication
@Configuration(proxyBeanMethods = false)
public class LogAutoConfiguration {

	@Bean
	public SysLogListener sysLogListener(RemoteLogService remoteLogService) {
		return new SysLogListener(remoteLogService);
	}

	@Bean
	public SysLogAspect sysLogAspect() {
		return new SysLogAspect();
	}

}
