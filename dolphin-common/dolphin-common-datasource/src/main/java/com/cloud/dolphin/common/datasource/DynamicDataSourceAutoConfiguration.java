package com.cloud.dolphin.common.datasource;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.cloud.dolphin.common.datasource.config.DataSourceProperties;
import com.cloud.dolphin.common.datasource.config.JdbcDynamicDataSourceProvider;
import com.cloud.dolphin.common.datasource.config.LastParamDsProcessor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *<p>
 * 动态数据源切换配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/19
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DynamicDataSourceAutoConfiguration {

	@Bean
	public DynamicDataSourceProvider dynamicDataSourceProvider(StringEncryptor stringEncryptor, DataSourceProperties properties) {
		return new JdbcDynamicDataSourceProvider(stringEncryptor, properties);
	}

	@Bean
	public DsProcessor dsProcessor() {
		return new LastParamDsProcessor();
	}

}
