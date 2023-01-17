package com.cloud.dolphin.common.seata.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *<p>
 * seata配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/3/11
 */
@Data
@ConfigurationProperties(prefix = "seata")
public class SeataProperties {

  private String applicationId;

  private String txServiceGroup;

}
