package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.discovery.AppManagement;
import com.alibaba.csp.sentinel.dashboard.discovery.MachineInfo;
import com.alibaba.csp.sentinel.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Eric Zhao
 */
@Component("flowRuleDefaultProvider")
public class FlowRuleApiProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {

	@Autowired
	private SentinelApiClient sentinelApiClient;

	@Autowired
	private AppManagement appManagement;

	@Override
	public List<FlowRuleEntity> getRules(String appName) throws Exception {
		if (StringUtil.isBlank(appName)) {
			return new ArrayList<>();
		}
		List<MachineInfo> list = appManagement.getDetailApp(appName).getMachines().stream()
				.filter(MachineInfo::isHealthy)
				.sorted((e1, e2) -> Long.compare(e2.getLastHeartbeat(), e1.getLastHeartbeat()))
				.collect(Collectors.toList());
		if (list.isEmpty()) {
			return new ArrayList<>();
		}
		else {
			MachineInfo machine = list.get(0);
			return sentinelApiClient.fetchFlowRuleOfMachine(machine.getApp(), machine.getIp(), machine.getPort());
		}
	}

}
