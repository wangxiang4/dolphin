package com.cloud.dolphin.common.security.feign;

import cn.hutool.core.collection.CollUtil;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.commons.security.AccessTokenContextRelay;
import org.springframework.cloud.openfeign.security.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

import java.util.Collection;

/**
 *<p>
 * 扩展OAuth2FeignRequestInterceptor
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@Slf4j
public class DolphinFeignClientInterceptor extends OAuth2FeignRequestInterceptor {

	private final OAuth2ClientContext oAuth2ClientContext;

	private final AccessTokenContextRelay accessTokenContextRelay;

	/**
	 * 在授权标头中使用提供的 OAuth2ClientContext 和 Bearer 令牌的默认构造函数
	 * @param oAuth2ClientContext provided context
	 * @param resource type of resource to be accessed
	 * @param accessTokenContextRelay
	 */
	public DolphinFeignClientInterceptor(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails resource,
										 AccessTokenContextRelay accessTokenContextRelay) {
		super(oAuth2ClientContext, resource);
		this.oAuth2ClientContext = oAuth2ClientContext;
		this.accessTokenContextRelay = accessTokenContextRelay;
	}

	/**
	 * 使用提供的名称和提取的提取物的标题创建模板
	 * 1. 如果使用 非web 请求，header 区别
	 * 2. 根据authentication 还原请求token
	 * @param template
	 */
	@Override
	public void apply(RequestTemplate template) {
		Collection<String> fromHeader = template.headers().get(SecurityConstants.FROM);
		if (CollUtil.isNotEmpty(fromHeader) && fromHeader.contains(SecurityConstants.FROM_IN)) {
			return;
		}
		// 进行token中转,传递token
		accessTokenContextRelay.copyToken();
		if (oAuth2ClientContext != null && oAuth2ClientContext.getAccessToken() != null) {
			super.apply(template);
		}
	}

}
