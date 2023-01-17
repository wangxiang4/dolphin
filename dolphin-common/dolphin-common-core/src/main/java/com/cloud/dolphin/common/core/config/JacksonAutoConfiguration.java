package com.cloud.dolphin.common.core.config;

import cn.hutool.core.date.DatePattern;
import com.cloud.dolphin.common.core.jackson.DolphinJavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 *<p>
 * 配置全局JacksonConfig,影响mvc层的对象传输日期格式
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/18
 */
@EnableAutoConfiguration
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class)
public class JacksonAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public Jackson2ObjectMapperBuilderCustomizer customizer() {
		return builder -> {
			builder.locale(Locale.CHINA);
			builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
			// 针对于Date类型,文本格式化,已经实现前端返回时间戳
			builder.simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
			// 解决返回给前端的Long类型数据失去精度,将Long转换为String
			builder.serializerByType(Long.class, ToStringSerializer.instance);
			// 针对于JDK新时间类,序列化时带有T的问题,自定义格式化字符串
			builder.modules(new DolphinJavaTimeModule());
		};
	}

}
