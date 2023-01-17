package com.cloud.dolphin.auth.config;

import com.cloud.dolphin.common.security.grant.provider.DolphinDaoAuthenticationProvider;
import com.cloud.dolphin.common.security.grant.provider.CustomAppAuthenticationProvider;
import com.cloud.dolphin.common.security.handler.FormAuthenticationFailureHandler;
import com.cloud.dolphin.common.security.handler.SsoLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 *<p>
 * 认证相关配置
 * 并且设置为主,以及加载优先级为1,防止被微服务工程覆盖
 *</p>
 *
 * @Author: 开发团队-王翔
 * @Date: 2022/2/16
 */

@Primary
@Order(90)
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	@SneakyThrows
	protected void configure(HttpSecurity http) {
		http
			.formLogin()
			.loginPage("/token/login")
			.loginProcessingUrl("/token/form")
			.failureHandler(authenticationFailureHandler())
			.and()
			.logout()
			.logoutSuccessHandler(logoutSuccessHandler())
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
			.and()
			.authorizeRequests()
			.antMatchers("/token/**", "/actuator/**", "/mobile/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			// CRSF禁用，因为不使用session
			.csrf().disable();
	}

	/**
	 * 自定义 provider 列表注入
	 * @param auth AuthenticationManagerBuilder
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		DolphinDaoAuthenticationProvider daoAuthenticationProvider = new DolphinDaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		// 处理默认的密码模式认证
		auth.authenticationProvider(daoAuthenticationProvider);
		// 自定义的认证模式
		auth.authenticationProvider(new CustomAppAuthenticationProvider());
	}

	@Bean
	@Override
	@SneakyThrows
	public AuthenticationManager authenticationManagerBean() {
		return super.authenticationManagerBean();
	}

	/**
	 * 认证中心静态资源处理
	 * @param web WebSecurity
	 */
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/css/**");
	}

	/**
	 * sso 表单登录失败处理
	 * @return FormAuthenticationFailureHandler
	 */
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new FormAuthenticationFailureHandler();
	}

	/**
	 * SSO 退出逻辑处理
	 * @return LogoutSuccessHandler
	 */
	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new SsoLogoutSuccessHandler();
	}

	/**
	 * 密码处理器
	 * @return 动态密码处理器 {类型}密文
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
