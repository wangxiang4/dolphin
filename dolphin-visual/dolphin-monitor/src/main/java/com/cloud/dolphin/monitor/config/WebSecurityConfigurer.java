package com.cloud.dolphin.monitor.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 *<p>
 * WebSecurityConfigurer
 * 配置spring-boot-admin资源不拦截
 * 以及配置确保spring-boot-admin访问安全的配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Configuration(proxyBeanMethods = false)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	private final String adminContextPath;

	public WebSecurityConfigurer(AdminServerProperties adminServerProperties) {
		this.adminContextPath = adminServerProperties.getContextPath();
	}

	@Override
	@SneakyThrows
	protected void configure(HttpSecurity http) {
		SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");
		successHandler.setDefaultTargetUrl(adminContextPath + "/");

		http
			.headers().frameOptions().disable()
			.and().authorizeRequests()
			.antMatchers(adminContextPath + "/assets/**"
				, adminContextPath + "/login"
				, adminContextPath + "/instances/**"
				, adminContextPath + "/actuator/**"
			).permitAll()
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage(adminContextPath + "/login")
			.successHandler(successHandler).and()
			.logout().logoutUrl(adminContextPath + "/logout")
			.and()
			.httpBasic()
			.and()
			.csrf()
			.disable();
	}

}
