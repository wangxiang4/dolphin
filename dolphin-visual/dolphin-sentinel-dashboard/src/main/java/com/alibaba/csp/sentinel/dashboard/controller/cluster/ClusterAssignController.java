package com.alibaba.csp.sentinel.dashboard.controller.cluster;

import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.domain.cluster.ClusterAppAssignResultVO;
import com.alibaba.csp.sentinel.dashboard.domain.cluster.ClusterAppFullAssignRequest;
import com.alibaba.csp.sentinel.dashboard.domain.cluster.ClusterAppSingleServerAssignRequest;
import com.alibaba.csp.sentinel.dashboard.service.ClusterAssignService;
import com.alibaba.csp.sentinel.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

/**
 * @author Eric Zhao
 * @since 1.4.1
 */
@RestController
@RequestMapping("/cluster/assign")
public class ClusterAssignController {

	private final Logger logger = LoggerFactory.getLogger(ClusterAssignController.class);

	@Autowired
	private ClusterAssignService clusterAssignService;

	@PostMapping("/all_server/{app}")
	public Result<ClusterAppAssignResultVO> apiAssignAllClusterServersOfApp(@PathVariable String app,
			@RequestBody ClusterAppFullAssignRequest assignRequest) {
		if (StringUtil.isEmpty(app)) {
			return Result.ofFail(-1, "app cannot be null or empty");
		}
		if (assignRequest == null || assignRequest.getClusterMap() == null
				|| assignRequest.getRemainingList() == null) {
			return Result.ofFail(-1, "bad request body");
		}
		try {
			return Result.ofSuccess(clusterAssignService.applyAssignToApp(app, assignRequest.getClusterMap(),
					assignRequest.getRemainingList()));
		}
		catch (Throwable throwable) {
			logger.error("Error when assigning full cluster servers for app: " + app, throwable);
			return Result.ofFail(-1, throwable.getMessage());
		}
	}

	@PostMapping("/single_server/{app}")
	public Result<ClusterAppAssignResultVO> apiAssignSingleClusterServersOfApp(@PathVariable String app,
			@RequestBody ClusterAppSingleServerAssignRequest assignRequest) {
		if (StringUtil.isEmpty(app)) {
			return Result.ofFail(-1, "app cannot be null or empty");
		}
		if (assignRequest == null || assignRequest.getClusterMap() == null) {
			return Result.ofFail(-1, "bad request body");
		}
		try {
			return Result.ofSuccess(clusterAssignService.applyAssignToApp(app,
					Collections.singletonList(assignRequest.getClusterMap()), assignRequest.getRemainingList()));
		}
		catch (Throwable throwable) {
			logger.error("Error when assigning single cluster servers for app: " + app, throwable);
			return Result.ofFail(-1, throwable.getMessage());
		}
	}

	@PostMapping("/unbind_server/{app}")
	public Result<ClusterAppAssignResultVO> apiUnbindClusterServersOfApp(@PathVariable String app,
			@RequestBody Set<String> machineIds) {
		if (StringUtil.isEmpty(app)) {
			return Result.ofFail(-1, "app cannot be null or empty");
		}
		if (machineIds == null || machineIds.isEmpty()) {
			return Result.ofFail(-1, "bad request body");
		}
		try {
			return Result.ofSuccess(clusterAssignService.unbindClusterServers(app, machineIds));
		}
		catch (Throwable throwable) {
			logger.error("Error when unbinding cluster server {} for app <{}>", machineIds, app, throwable);
			return Result.ofFail(-1, throwable.getMessage());
		}
	}

}
