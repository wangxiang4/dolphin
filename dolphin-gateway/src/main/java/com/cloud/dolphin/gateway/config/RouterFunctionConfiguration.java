package com.cloud.dolphin.gateway.config;

import com.cloud.dolphin.gateway.handler.ImageCodeHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 *<p>
 * 路由配置信息
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class RouterFunctionConfiguration {

	private final ImageCodeHandler imageCodeHandler;

	@Bean
	public RouterFunction<ServerResponse> routerFunction() {
		// 配置获取验证码路由
		return RouterFunctions.route(RequestPredicates.path("/code").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), imageCodeHandler);
	}

}
