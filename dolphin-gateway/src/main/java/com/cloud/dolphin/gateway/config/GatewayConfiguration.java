package com.cloud.dolphin.gateway.config;

import com.cloud.dolphin.gateway.filter.DolphinRequestGlobalFilter;
import com.cloud.dolphin.gateway.filter.PasswordDecoderFilter;
import com.cloud.dolphin.gateway.filter.ValidateCodeGatewayFilter;
import com.cloud.dolphin.gateway.handler.GlobalExceptionHandler;
import com.cloud.dolphin.gateway.handler.ImageCodeHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *<p>
 * 网关配置
 * 主要配置注入一些过滤器方便网关调用以及一些处理需求
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(GatewayConfigProperties.class)
public class GatewayConfiguration {

	@Bean
	public PasswordDecoderFilter passwordDecoderFilter(GatewayConfigProperties configProperties) {
		return new PasswordDecoderFilter(configProperties);
	}

	@Bean
	public DolphinRequestGlobalFilter dolphinRequestGlobalFilter() {
		return new DolphinRequestGlobalFilter();
	}

	@Bean
	public ValidateCodeGatewayFilter validateCodeGatewayFilter(GatewayConfigProperties configProperties, ObjectMapper objectMapper, RedisTemplate redisTemplate) {
		return new ValidateCodeGatewayFilter(configProperties, objectMapper, redisTemplate);
	}

	@Bean
	public GlobalExceptionHandler globalExceptionHandler(ObjectMapper objectMapper) {
		return new GlobalExceptionHandler(objectMapper);
	}

	@Bean
	public ImageCodeHandler imageCodeHandler(RedisTemplate redisTemplate) {
		return new ImageCodeHandler(redisTemplate);
	}

}
