package com.cloud.dolphin.common.mock.kit;

import com.cloud.dolphin.common.core.util.SpringContextHolderUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

/**
 *<p>
 * Mock 工具类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
public class OAuthMockKit {

	/**
	 * mock 请求增加统一请求头
	 * @return RequestPostProcessor 类似于拦截器
	 */
	public static RequestPostProcessor token() {
		return mockRequest -> {
			OAuth2ClientContext clientContext = SpringContextHolderUtil.getBean(OAuth2ClientContext.class);
			String token = clientContext.getAccessToken().getValue();
			mockRequest.addHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer: %s", token));
			return mockRequest;
		};
	}

}
