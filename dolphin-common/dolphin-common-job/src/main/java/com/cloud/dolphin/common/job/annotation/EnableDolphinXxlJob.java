package com.cloud.dolphin.common.job.annotation;

import com.cloud.dolphin.common.job.XxlJobAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *<p>
 * 激活xxl-job配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/19
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ XxlJobAutoConfiguration.class })
public @interface EnableDolphinXxlJob {

}
