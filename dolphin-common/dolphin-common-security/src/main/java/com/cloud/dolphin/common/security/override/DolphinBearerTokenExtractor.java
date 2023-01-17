package com.cloud.dolphin.common.security.override;

import com.cloud.dolphin.common.security.exp.PermitAllUrlProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 *<p>
 * 重写 {@link BearerTokenExtractor}
 * 对公开权限的请求不进行抓取校验直接返回null放过
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/19
 */
public class DolphinBearerTokenExtractor extends BearerTokenExtractor {

	private final PathMatcher pathMatcher;

	private final PermitAllUrlProperties urlProperties;

	public DolphinBearerTokenExtractor(PermitAllUrlProperties urlProperties) {
		this.urlProperties = urlProperties;
		this.pathMatcher = new AntPathMatcher();
	}

	@Override
	public Authentication extract(HttpServletRequest request) {
		boolean match = urlProperties.getUrls().stream()
				.anyMatch(url -> pathMatcher.match(url, request.getRequestURI()));

		return match ? null : super.extract(request);
	}

}
