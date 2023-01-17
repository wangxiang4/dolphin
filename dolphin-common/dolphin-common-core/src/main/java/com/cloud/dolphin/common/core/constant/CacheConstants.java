package com.cloud.dolphin.common.core.constant;

/**
 *<p>
 * 缓存的key 常量
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/18
 */
public interface CacheConstants {

	/**
	 * oauth 缓存前缀
	 */
	String OAUTH_ACCESS = ServiceNameConstants.AUTH_SERVICE + ":access:";

	/**
	 * oauth 缓存令牌前缀
	 */
	String OAUTH_TOKEN = ServiceNameConstants.AUTH_SERVICE + ":token:";

	/**
	 * 验证码前缀
	 */
	String VERIFICATION_CODE = ServiceNameConstants.SYSTEM_SERVICE + ":verification_code:";

	/**
	 * oauth 客户端信息缓存
	 */
	String OAUTH_CLIENT_DETAILS = ServiceNameConstants.AUTH_SERVICE + ":client_details";

	/**
	 * 菜单信息缓存
	 */
	String MENU_DETAILS = ServiceNameConstants.AUTH_SERVICE + ":menu_details";

	/**
	 * 用户信息缓存
	 */
	String USER_DETAILS =  ServiceNameConstants.AUTH_SERVICE + ":user_details";

	/**
	 * 字典信息缓存
	 */
	String DICT_DETAILS =  ServiceNameConstants.AUTH_SERVICE + ":dict_details";

	/**
	 * 全局配置缓存
	 */
	String CONFIG_PARAM =  ServiceNameConstants.AUTH_SERVICE + ":config_param";

}
