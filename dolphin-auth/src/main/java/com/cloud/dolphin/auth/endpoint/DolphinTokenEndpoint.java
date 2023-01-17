package com.cloud.dolphin.auth.endpoint;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.dolphin.common.core.constant.CacheConstants;
import com.cloud.dolphin.common.core.constant.CommonConstants;
import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.util.SpringContextHolderUtil;
import com.cloud.dolphin.common.security.annotation.Inner;
import com.cloud.dolphin.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *<p>
 * token端点管理
 *</p>
 *
 * @Author: 开发团队-王翔
 * @Date: 2022/2/16
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class DolphinTokenEndpoint {

	private final ClientDetailsService clientDetailsService;

	private final TokenStore tokenStore;

	private final RedisTemplate redisTemplate;

	private final CacheManager cacheManager;

	/**
	 * 认证页面
	 * @param modelAndView
	 * @param error 表单登录失败处理回调的错误信息
	 * @return ModelAndView
	 */
	@GetMapping("/login")
	public ModelAndView require(ModelAndView modelAndView, @RequestParam(required = false) String error) {
		modelAndView.setViewName("ftl/login");
		modelAndView.addObject("error", error);
		return modelAndView;
	}

	/**
	 * 确认授权页面
	 * @param request
	 * @param session
	 * @param modelAndView
	 * @return
	 */
	@GetMapping("/confirm_access")
	public ModelAndView confirm(HttpServletRequest request, HttpSession session, ModelAndView modelAndView) {
		Map<String, Object> scopeList = (Map<String, Object>) request.getAttribute("scopes");
		modelAndView.addObject("scopeList", scopeList.keySet());

		Object auth = session.getAttribute("authorizationRequest");
		if (auth != null) {
			AuthorizationRequest authorizationRequest = (AuthorizationRequest) auth;
			ClientDetails clientDetails = clientDetailsService.loadClientByClientId(authorizationRequest.getClientId());
			modelAndView.addObject("app", clientDetails.getAdditionalInformation());
			modelAndView.addObject("user", SecurityUtils.getUser());
		}

		modelAndView.setViewName("ftl/confirm");
		return modelAndView;
	}

	/**
	 * 退出并删除token
	 * @param authHeader Authorization
	 */
	@DeleteMapping("/logout")
	public R<Boolean> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
		if (StrUtil.isBlank(authHeader)) {
			return R.error("退出失败，token 为空");
		}
		String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
		OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
		if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
			return R.error("退出失败，token 无效");
		}
		// 清空access token
		tokenStore.removeAccessToken(accessToken);
		// 清空 refresh token
		OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
		tokenStore.removeRefreshToken(refreshToken);
		return R.ok();
	}

	/**
	 * 删除令牌
	 * @param token token
	 */
	@Inner
	@DeleteMapping("/{token}")
	@PreAuthorize("@ps.hasPerm('token_del')")
	public R<Boolean> removeToken(@PathVariable("token") String token) {
		OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
		if (oAuth2AccessToken == null || StrUtil.isBlank(oAuth2AccessToken.getValue())) {
			return R.ok(Boolean.TRUE, "操作失败，token 无效");
		}

		OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(oAuth2AccessToken);
		// 清空用户信息
		cacheManager.getCache(CacheConstants.USER_DETAILS).evict(auth2Authentication.getName());

		// 清空access token
		tokenStore.removeAccessToken(oAuth2AccessToken);

		// 清空 refresh token
		OAuth2RefreshToken refreshToken = oAuth2AccessToken.getRefreshToken();
		tokenStore.removeRefreshToken(refreshToken);

		// 处理自定义退出事件，保存相关日志
		SpringContextHolderUtil.publishEvent(new LogoutSuccessEvent(auth2Authentication));
		return R.ok(true);
	}

	/**
	 * 查询令牌
	 * @param params 分页参数
	 * @return
	 */
	@Inner
	@PostMapping("/list")
	@PreAuthorize("@ps.hasPerm('token_view')")
	public R tokenList(@RequestBody Map<String, Object> params) {
		// 根据分页参数获取对应数据
		String key = String.format("%sauth_to_access:*", CacheConstants.OAUTH_ACCESS);
		int current = MapUtil.getInt(params, CommonConstants.CURRENT);
		int size = MapUtil.getInt(params, CommonConstants.SIZE);
		Set<String> keys = redisTemplate.keys(key);
		List<String> pages = keys.stream().skip((current - 1) * size).limit(size).collect(Collectors.toList());
		Page result = new Page(current, size);
		result.setRecords(redisTemplate.opsForValue().multiGet(pages));
		result.setTotal(keys.size());
		return R.ok(result.getRecords(), result.getTotal());
	}

}
