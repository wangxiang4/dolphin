package com.cloud.dolphin.common.security.config;

import com.cloud.dolphin.common.security.exp.PermitAllUrlProperties;
import com.cloud.dolphin.common.security.exp.ResourceAuthExceptionEntryPoint;
import com.cloud.dolphin.common.security.override.DolphinBearerTokenExtractor;
import com.cloud.dolphin.common.security.exp.DolphinLocalResourceServerTokenServices;
import com.cloud.dolphin.common.security.exp.PermissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 *<p>
 * 扩展资源服务器自动配置
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@EnableConfigurationProperties(PermitAllUrlProperties.class)
public class ResourceServerAutoConfiguration {

	/** 校验接口是否存在权限 */
	@Bean("pms")
	public PermissionService permissionService() {
		return new PermissionService();
	}

	/** 对公开权限的请求获取token不进行校验 */
	@Bean
	public DolphinBearerTokenExtractor dolphinBearerTokenExtractor(PermitAllUrlProperties urlProperties) {
		return new DolphinBearerTokenExtractor(urlProperties);
	}

	/** 细粒化客户端认证异常处理 */
	@Bean
	public ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint(ObjectMapper objectMapper) {
		return new ResourceAuthExceptionEntryPoint(objectMapper);
	}

	/** 扩展资源服务器令牌服务 */
	@Bean
	@Primary
	public ResourceServerTokenServices resourceServerTokenServices(TokenStore tokenStore) {
		return new DolphinLocalResourceServerTokenServices(tokenStore);
	}

}
