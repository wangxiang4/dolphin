package com.cloud.dolphin.common.mock.annotation;

import com.cloud.dolphin.common.mock.WithMockSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *<p>
 * WithMockOAuth2User 注解
 * 用于单元测试模拟登录用户测试接口
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockSecurityContextFactory.class)
public @interface WithMockOAuth2User {

	/**
	 * 用户名
	 */
	String username() default "admin";

	/**
	 * 密码
	 */
	String password() default "123456";

}
