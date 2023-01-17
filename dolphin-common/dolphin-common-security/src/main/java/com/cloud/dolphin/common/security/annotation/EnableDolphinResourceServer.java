package com.cloud.dolphin.common.security.annotation;

import com.cloud.dolphin.common.security.config.ResourceServerAutoConfiguration;
import com.cloud.dolphin.common.security.config.ResourceServerTokenRelayAutoConfiguration;
import com.cloud.dolphin.common.security.exp.DolphinSecurityBeanDefinitionRegistrar;
import com.cloud.dolphin.common.security.feign.DolphinFeignClientConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 *<p>
 * 扩展资源服务注解
 * 添加方法安全级别
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Documented
@Inherited
@EnableResourceServer
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ ResourceServerAutoConfiguration.class, DolphinSecurityBeanDefinitionRegistrar.class,
		ResourceServerTokenRelayAutoConfiguration.class, DolphinFeignClientConfiguration.class })
public @interface EnableDolphinResourceServer {

}
