package com.cloud.dolphin.common.swagger.support;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 *<p>
 * 聚合接口文档注册，和zuul实现类似
 * 获取网关重新截取网关代理前缀拼接/v2/api-docs,创建resources文档
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Primary
@RequiredArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {

	private static final String API_URI = "/v2/api-docs";

	private final SwaggerProperties swaggerProperties;

	private final GatewayProperties gatewayProperties;

	@Lazy
	@Autowired
	private RouteLocator routeLocator;

	@Override
	public List<SwaggerResource> get() {
		List<SwaggerResource> resources = new ArrayList<>();
		List<String> routes = new ArrayList<>();
		// 添加网关路由Id
		routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
		// 当前加载进内存的网关路由去匹配yml中配置的网关路由匹配则添加到Swagger中
		gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
				.forEach(routeDefinition -> routeDefinition.getPredicates().stream()
						.filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
						.filter(predicateDefinition -> !swaggerProperties.getIgnoreProviders()
								.contains(routeDefinition.getId()))
						.forEach(predicateDefinition -> resources
								.add(swaggerResource(routeDefinition.getId(), predicateDefinition.getArgs()
										.get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", API_URI)))));
		return resources;
	}

	private static SwaggerResource swaggerResource(String name, String location) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion("2.0");
		return swaggerResource;
	}

}
