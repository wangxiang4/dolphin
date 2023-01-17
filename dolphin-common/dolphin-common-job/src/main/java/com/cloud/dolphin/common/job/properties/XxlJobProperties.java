package com.cloud.dolphin.common.job.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 *<p>
 * xxl-job配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/19
 */
@Data
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

	@NestedConfigurationProperty
	private XxlAdminProperties admin = new XxlAdminProperties();

	@NestedConfigurationProperty
	private XxlExecutorProperties executor = new XxlExecutorProperties();

}
