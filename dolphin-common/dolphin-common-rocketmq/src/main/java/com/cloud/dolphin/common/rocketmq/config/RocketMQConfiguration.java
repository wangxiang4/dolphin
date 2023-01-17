package com.cloud.dolphin.common.rocketmq.config;

import com.cloud.dolphin.common.core.factory.YamlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *<p>
 * RocketMQ配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/9
 */
@Configuration
@PropertySource(factory = YamlPropertySourceFactory.class, value = "classpath:dolphin-rocketmq.yml")
public class RocketMQConfiguration {
}
