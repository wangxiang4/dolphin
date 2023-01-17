package com.cloud.dolphin.common.data.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.cloud.dolphin.common.core.factory.YamlPropertySourceFactory;
import com.cloud.dolphin.common.data.handler.BaseMetaObjectHandler;
import com.cloud.dolphin.common.data.handler.DolphinTenantLineHandler;
import com.cloud.dolphin.common.data.plugins.DolphinPaginationInnerInterceptor;
import com.cloud.dolphin.common.data.plugins.DolphinTenantLineInnerInterceptor;
import com.cloud.dolphin.common.data.properties.TenantProperties;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *<p>
 * mybatis plus 统一配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/18
 */
@Configuration(proxyBeanMethods = false)
@MapperScan("com.cloud.dolphin.*.mapper")
@EnableConfigurationProperties(TenantProperties.class)
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:dolphin-tenant.yml")
@RequiredArgsConstructor
public class MybatisConfiguration implements WebMvcConfigurer {

	private final TenantProperties tenantProperties;

	/**
	 * 插件配置
	 * 多租户插件, 自动拼接多租户id进行增删改查
	 * 分页插件, 对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new DolphinTenantLineInnerInterceptor(new DolphinTenantLineHandler(tenantProperties)));
		interceptor.addInnerInterceptor(new DolphinPaginationInnerInterceptor());
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		return interceptor;
	}

	/**
	 * 审计字段自动填充
	 * @return {@link MetaObjectHandler}
	 */
	@Bean
	public BaseMetaObjectHandler mybatisPlusMetaObjectHandler() {
		return new BaseMetaObjectHandler();
	}

}
