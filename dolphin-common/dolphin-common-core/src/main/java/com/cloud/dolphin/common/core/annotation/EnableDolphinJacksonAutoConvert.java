package com.cloud.dolphin.common.core.annotation;

import com.cloud.dolphin.common.core.config.JacksonAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *<p>
 * 激活 Jackson 自动转换配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/19
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ JacksonAutoConfiguration.class })
public @interface EnableDolphinJacksonAutoConvert {
}
