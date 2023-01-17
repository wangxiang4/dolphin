package com.cloud.dolphin.common.data.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 *<p>
 * 多租户配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/4/2+
 */
@Data
@ConfigurationProperties(prefix = "tenant")
public class TenantProperties {

    private Boolean enableInsert;

    private List<String> exclusionTable;

}
