package com.cloud.dolphin.common.datasource.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *<p>
 * 多数据源配置属性
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/19
 */
@Data
@ConfigurationProperties("spring.datasource")
public class DataSourceProperties {

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * jdbc_url
	 */
	private String url;

	/**
	 * 驱动类型
	 */
	private String driverClassName;

	/**
	 * 查询数据源的SQL
	 */
	private String queryDsSql = "select * from sys_dynamic_datasource where del_flag = 0";

}
