package com.cloud.dolphin.common.security.exp;

import com.cloud.dolphin.common.security.override.DolphinBearerTokenExtractor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 *<p>
 * 资源服务配置适配器
 * 覆盖内部不合适的实现,实现适配
 * 1. 支持remoteTokenServices 负载均衡,后期出现
 * 2. 支持 获取用户全部信息
 * 3. 接口对外暴露，不校验 Authentication Header 头
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Slf4j
public class DolphinResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

	@Autowired
	protected ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;

	@Autowired
	protected RemoteTokenServices remoteTokenServices;

	@Autowired
	private PermitAllUrlProperties permitAllUrl;

	@Autowired
	private DolphinBearerTokenExtractor dolphinBearerTokenExtractor;

	@Autowired
	private ResourceServerTokenServices resourceServerTokenServices;

	/**
	 * 默认的配置，对外暴露
	 * @param httpSecurity
	 */
	@Override
	@SneakyThrows
	public void configure(HttpSecurity httpSecurity) {
		// 允许使用iframe 嵌套,避免swagger-ui 不被加载的问题
		httpSecurity.headers().frameOptions().disable();
		// 设置所有的请求必须通过授权认证才可以访问
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
		// 设置添加了系统内部调用注解,才对外提供访问,也可以理解SOA架构互相调用,以及配置了忽略url
		permitAllUrl.getUrls().forEach(url -> registry.antMatchers(url).permitAll());
		registry.anyRequest().authenticated().and().csrf().disable();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.authenticationEntryPoint(resourceAuthExceptionEntryPoint).tokenExtractor(dolphinBearerTokenExtractor)
				.tokenServices(resourceServerTokenServices);
	}

}
