package com.alibaba.csp.sentinel.dashboard.rule;

import com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.discovery.AppManagement;
import com.alibaba.csp.sentinel.dashboard.discovery.MachineInfo;
import com.alibaba.csp.sentinel.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
@Component("flowRuleDefaultPublisher")
public class FlowRuleApiPublisher implements DynamicRulePublisher<List<FlowRuleEntity>> {

	@Autowired
	private SentinelApiClient sentinelApiClient;

	@Autowired
	private AppManagement appManagement;

	@Override
	public void publish(String app, List<FlowRuleEntity> rules) throws Exception {
		if (StringUtil.isBlank(app)) {
			return;
		}
		if (rules == null) {
			return;
		}
		Set<MachineInfo> set = appManagement.getDetailApp(app).getMachines();

		for (MachineInfo machine : set) {
			if (!machine.isHealthy()) {
				continue;
			}
			// TODO: parse the results
			sentinelApiClient.setFlowRuleOfMachine(app, machine.getIp(), machine.getPort(), rules);
		}
	}

}
