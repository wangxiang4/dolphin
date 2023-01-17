package com.cloud.dolphin.common.security.feign;

import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.commons.security.AccessTokenContextRelay;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

/**
 *<p>
 * 拦截器传递 header 中oauth token,使用hystrix 的信号量模式
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@ConditionalOnProperty("security.oauth2.client.client-id")
public class DolphinFeignClientConfiguration {

	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource, AccessTokenContextRelay accessTokenContextRelay) {
		return new DolphinFeignClientInterceptor(oAuth2ClientContext, resource, accessTokenContextRelay);
	}

}
