package com.cloud.dolphin.common.security.xss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *<p>
 * xss 配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/24
 */
@Data
@ConfigurationProperties(prefix = "xss")
public class XssProperties {

    private String enabled;

    private String excludes;

    private String urlPatterns;

}
