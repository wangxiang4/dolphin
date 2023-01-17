package com.cloud.dolphin.gateway.filter;

import com.cloud.dolphin.common.core.constant.SecurityConstants;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 *<p>
 * 全局拦截器,作用所有的微服务
 * 1.对请求头中参数进行处理 from 参数进行清洗
 * 2.重写StripPrefix = 1,支持全局支持swagger添加X-Forwarded-Prefix header（F SR2 已经支持,不需要自己维护）
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
public class DolphinRequestGlobalFilter implements GlobalFilter, Ordered {

	/**
	 * Process the Web request and (optionally) delegate to the next {@code WebFilter}
	 * through the given {@link GatewayFilterChain}.
	 * @param exchange the current server exchange
	 * @param chain provides a way to delegate to the next filter
	 * @return {@code Mono<Void>} to indicate when request processing is complete
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 1. 清洗请求头中from 参数
		ServerHttpRequest request = exchange.getRequest().mutate()
				.headers(httpHeaders -> httpHeaders.remove(SecurityConstants.FROM)).build();

		// 2. 重写StripPrefix,重写请求路径,去掉网关代理前缀标识,原因要去请求真实地址,如果携带前缀标识会找不到地址
		addOriginalRequestUrl(exchange, request.getURI());
		String rawPath = request.getURI().getRawPath();
		String newPath = "/" + Arrays.stream(StringUtils.tokenizeToStringArray(rawPath, "/")).skip(1L)
				.collect(Collectors.joining("/"));
		ServerHttpRequest newRequest = request.mutate().path(newPath).build();
		exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());

		return chain.filter(exchange.mutate().request(newRequest.mutate().build()).build());
	}

	@Override
	public int getOrder() {
		return -1000;
	}

}
