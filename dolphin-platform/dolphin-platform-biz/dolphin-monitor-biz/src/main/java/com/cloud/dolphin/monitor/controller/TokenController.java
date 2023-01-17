package com.cloud.dolphin.monitor.controller;

import com.cloud.dolphin.common.core.api.R;
import com.cloud.dolphin.common.core.constant.AppConstants;
import com.cloud.dolphin.common.core.constant.SecurityConstants;
import com.cloud.dolphin.monitor.api.feign.RemoteTokenService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *<p>
 * token管理
 *</p>
 *
 * @Author: entfrm开发团队-王翔
 * @Date: 2022/2/21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.APP_MONITOR + "/token")
@Api(value = "token", tags = "令牌管理模块")
public class TokenController {

	private final RemoteTokenService remoteTokenService;

	@GetMapping("/list")
	public R token(@RequestParam Map<String, Object> params) {
		return remoteTokenService.getTokenPage(params, SecurityConstants.FROM_IN);
	}

	@DeleteMapping("/remove/{id}")
	@PreAuthorize("@pms.hasPermission('token_del')")
	public R<Boolean> delete(@PathVariable String id) {
		return remoteTokenService.removeToken(id, SecurityConstants.FROM_IN);
	}

}
