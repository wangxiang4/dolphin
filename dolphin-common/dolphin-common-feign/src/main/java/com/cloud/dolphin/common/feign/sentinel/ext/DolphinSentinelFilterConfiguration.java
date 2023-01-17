package com.cloud.dolphin.common.feign.sentinel.ext;

import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.DefaultBlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.UrlCleaner;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 *<p>
 * 避免哨兵拦截请求 与 spring cloud 2021 不兼容 的问题
 * 重新注入Sentinel拦截核心Bean
 * 会出现:The dependencies of some of the beans in the application context form a cycle 循环依赖问题
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class DolphinSentinelFilterConfiguration {

	@Bean
	public SentinelWebInterceptor sentinelWebInterceptor(SentinelWebMvcConfig sentinelWebMvcConfig) {
		return new SentinelWebInterceptor(sentinelWebMvcConfig);
	}

	@Bean
	public SentinelWebMvcConfig sentinelWebMvcConfig(SentinelProperties properties,
			Optional<UrlCleaner> urlCleanerOptional, Optional<BlockExceptionHandler> blockExceptionHandlerOptional,
			Optional<RequestOriginParser> requestOriginParserOptional) {
		SentinelWebMvcConfig sentinelWebMvcConfig = new SentinelWebMvcConfig();
		sentinelWebMvcConfig.setHttpMethodSpecify(properties.getHttpMethodSpecify());
		sentinelWebMvcConfig.setWebContextUnify(properties.getWebContextUnify());

		if (blockExceptionHandlerOptional.isPresent()) {
			blockExceptionHandlerOptional.ifPresent(sentinelWebMvcConfig::setBlockExceptionHandler);
		} else {
			if (StringUtils.hasText(properties.getBlockPage())) {
				sentinelWebMvcConfig.setBlockExceptionHandler(
						((request, response, e) -> response.sendRedirect(properties.getBlockPage())));
			} else {
				sentinelWebMvcConfig.setBlockExceptionHandler(new DefaultBlockExceptionHandler());
			}
		}

		urlCleanerOptional.ifPresent(sentinelWebMvcConfig::setUrlCleaner);
		requestOriginParserOptional.ifPresent(sentinelWebMvcConfig::setOriginParser);
		return sentinelWebMvcConfig;
	}

}
