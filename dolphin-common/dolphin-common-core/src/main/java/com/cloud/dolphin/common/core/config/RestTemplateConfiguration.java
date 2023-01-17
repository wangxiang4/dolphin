package com.cloud.dolphin.common.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 *<p>
 * Rest 配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/18
 */
@Configuration(proxyBeanMethods = false)
public class RestTemplateConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
