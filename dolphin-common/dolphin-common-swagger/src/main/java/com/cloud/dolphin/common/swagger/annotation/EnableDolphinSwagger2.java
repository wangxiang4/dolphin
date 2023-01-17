package com.cloud.dolphin.common.swagger.annotation;

import com.cloud.dolphin.common.swagger.config.GatewaySwaggerAutoConfiguration;
import com.cloud.dolphin.common.swagger.config.SwaggerAutoConfiguration;
import com.cloud.dolphin.common.swagger.support.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *<p>
 * 开启 swagger2
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(SwaggerProperties.class)
@Import({ SwaggerAutoConfiguration.class, GatewaySwaggerAutoConfiguration.class })
public @interface EnableDolphinSwagger2 {

}
