package com.cloud.dolphin.common.security.exp;

import com.cloud.dolphin.common.data.entity.DolphinUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 *<p>
 * 本地资源服务器令牌服务
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@RequiredArgsConstructor
public class DolphinLocalResourceServerTokenServices implements ResourceServerTokenServices {

	private final TokenStore tokenStore;

	@Override
	public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
		// 根据token加载身份验证
		OAuth2Authentication oAuth2Authentication = tokenStore.readAuthentication(accessToken);
		if (oAuth2Authentication == null) {
			return null;
		}

		OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
		// 检测是否是属于认证的DolphinUser实体用户
		if (!(oAuth2Authentication.getPrincipal() instanceof DolphinUser)) {
			return oAuth2Authentication;
		}

		DolphinUser dolphinUser = (DolphinUser) oAuth2Authentication.getPrincipal();
		// 每次请求前都预先加载用户名密码身份验证令牌
		Authentication userAuthentication = new UsernamePasswordAuthenticationToken(dolphinUser, "N/A", dolphinUser.getAuthorities());
		OAuth2Authentication authentication = new OAuth2Authentication(oAuth2Request, userAuthentication);
		authentication.setAuthenticated(true);
		return authentication;
	}

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		throw new UnsupportedOperationException("Not supported: read access token");
	}

}
