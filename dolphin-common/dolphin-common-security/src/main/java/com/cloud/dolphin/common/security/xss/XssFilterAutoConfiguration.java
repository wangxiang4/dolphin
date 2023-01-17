package com.cloud.dolphin.common.security.xss;

import cn.hutool.core.util.StrUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

/**
 *<p>
 * XSS-Filter注册
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/19
 */
@EnableConfigurationProperties(XssProperties.class)
public class XssFilterAutoConfiguration {

    @Bean
    public FilterRegistrationBean xssFilterRegistration(XssProperties xssProperties) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(StrUtil.splitToArray(xssProperties.getUrlPatterns(), ","));
        registration.setName("xssFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        Map<String, String> initParameters = new HashMap();
        initParameters.put("excludes", xssProperties.getExcludes());
        initParameters.put("enabled", xssProperties.getEnabled());
        registration.setInitParameters(initParameters);
        return registration;
    }

}
