package com.cloud.dolphin.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 *<p>
 * 网关配置文件
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Data
@RefreshScope
@ConfigurationProperties("gateway")
public class GatewayConfigProperties {

	/**
	 * 网关解密登录前端密码 秘钥 {@link com.cloud.dolphin.gateway.filter.PasswordDecoderFilter}
	 */
	private String encodeKey;

	/**
	 * 网关忽略不需要校验验证码是否合法的客户端 {@link com.cloud.dolphin.gateway.filter.ValidateCodeGatewayFilter}
	 */
	private List<String> ignoreClients;

}
