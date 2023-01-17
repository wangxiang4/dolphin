package com.alibaba.csp.sentinel.dashboard.controller;

import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hisenyuan
 * @since 1.7.0
 */
@RestController
public class VersionController {

	private static final String VERSION_PATTERN = "-";

	@Value("${sentinel.dashboard.version:}")
	private String sentinelDashboardVersion;

	@GetMapping("/version")
	public Result<String> apiGetVersion() {
		if (StringUtil.isNotBlank(sentinelDashboardVersion)) {
			String res = sentinelDashboardVersion;
			if (sentinelDashboardVersion.contains(VERSION_PATTERN)) {
				res = sentinelDashboardVersion.substring(0, sentinelDashboardVersion.indexOf(VERSION_PATTERN));
			}
			return Result.ofSuccess(res);
		}
		else {
			return Result.ofFail(1, "getVersion failed: empty version");
		}
	}

}
