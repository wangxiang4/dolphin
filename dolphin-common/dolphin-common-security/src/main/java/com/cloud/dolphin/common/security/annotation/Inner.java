package com.cloud.dolphin.common.security.annotation;

import java.lang.annotation.*;

/**
 *<p>
 * 服务调用不鉴权注解
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inner {

	/**
	 * 是否AOP统一处理
	 * @return false, true
	 */
	boolean value() default true;

	/**
	 * 为后续扩展做准备,需要特殊判空的字段(预留)
	 * @return {}
	 */
	String[] field() default {};

}
