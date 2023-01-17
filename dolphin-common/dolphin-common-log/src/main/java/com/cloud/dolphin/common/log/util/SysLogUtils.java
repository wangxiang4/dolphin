package com.cloud.dolphin.common.log.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.cloud.dolphin.common.data.entity.DolphinUser;
import com.cloud.dolphin.common.log.menus.LogTypeEnum;
import com.cloud.dolphin.monitor.api.entity.OperLog;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 *<p>
 * 系统日志工具类
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/17
 */
@UtilityClass
public class SysLogUtils {

	public OperLog getSysLog() {
		HttpServletRequest request = ((ServletRequestAttributes) Objects
				.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		OperLog sysLog = new OperLog();
		sysLog.setOperIp(ServletUtil.getClientIP(request));
		sysLog.setType(LogTypeEnum.NORMAL.getType());
		sysLog.setOperAddr(ServletUtil.getClientIP(request));
		sysLog.setOperUrl(URLUtil.getPath(request.getRequestURI()));
		sysLog.setMethod(request.getMethod());
		sysLog.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
		sysLog.setOperParam(HttpUtil.toParams(request.getParameterMap()));
		sysLog.setClientId(getClientId(request));
		sysLog.setServiceId(getClientId(request));
		sysLog.setOperTime(LocalDateTime.now());
		if (ObjectUtil.isNotEmpty(getUser())) {
			sysLog.setOperName(getUser().getUsername());
			sysLog.setCreateById(getUser().getId());
			sysLog.setCreateByName(getUser().getUsername());
			sysLog.setUpdateById(getUser().getId());
			sysLog.setUpdateByName(getUser().getUsername());
		}
		return sysLog;
	}

	/**
	 * 获取客户端
	 * @return clientId
	 */
	private String getClientId(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
			return auth2Authentication.getOAuth2Request().getClientId();
		}
		if (authentication instanceof UsernamePasswordAuthenticationToken) {
			BasicAuthenticationConverter basicAuthenticationConverter = new BasicAuthenticationConverter();
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = basicAuthenticationConverter
					.convert(request);
			if (usernamePasswordAuthenticationToken != null) {
				return usernamePasswordAuthenticationToken.getName();
			}
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	protected DolphinUser getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (Optional.ofNullable(authentication).isPresent()) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof DolphinUser) {
				return (DolphinUser) principal;
			}
		}
		return null;
	}

}
